package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _latestInfo = MutableStateFlow("🔥 Sách cũ mà mới - Giảm giá 70%! 🔥")
    val latestInfo: StateFlow<String> = _latestInfo

    private val _shippingAddress = MutableStateFlow("Đang tải địa chỉ...")
    val shippingAddress: StateFlow<String> = _shippingAddress

    init {
        loadShippingAddressFromFirestore()
    }

    private fun loadShippingAddressFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    val doc = db.collection("users").document(user.uid).get().await()
                    val address = doc.getString("address") ?: "Chưa có địa chỉ"
                    _shippingAddress.value = address
                } catch (e: Exception) {
                    _shippingAddress.value = "Lỗi tải địa chỉ"
                }
            }
        } else {
            _shippingAddress.value = "Chưa đăng nhập"
        }
    }

    fun updateShippingAddress(newAddress: String) {
        viewModelScope.launch {
            _shippingAddress.emit(newAddress)
        }
    }
}
