package com.example.book14.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AccountDetailViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    val avatarUrl = mutableStateOf("")
    val message = mutableStateOf("")
    val loading = mutableStateOf(true)

    fun loadUserData() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    val snapshot = db.collection("users").document(user.uid).get().await()
                    username.value = snapshot.getString("username") ?: ""
                    email.value = snapshot.getString("email") ?: ""
                    address.value = snapshot.getString("address") ?: ""
                    phoneNumber.value = snapshot.getString("phoneNumber") ?: ""
                    avatarUrl.value = snapshot.getString("avatarUrl") ?: ""
                    loading.value = false
                } catch (e: Exception) {
                    message.value = "Lỗi tải dữ liệu: ${e.message}"
                    loading.value = false
                }
            }
        } else {
            loading.value = false
        }
    }

    fun updateUserData() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    db.collection("users").document(user.uid).update(
                        mapOf(
                            "username" to username.value,
                            "address" to address.value,
                            "phoneNumber" to phoneNumber.value,
                            "avatarUrl" to avatarUrl.value
                        )
                    ).await()
                    message.value = "Cập nhật thành công!"
                } catch (e: Exception) {
                    message.value = "Lỗi cập nhật: ${e.message}"
                }
            }
        }
    }

    fun updateAvatarUrl(newUrl: String) {
        avatarUrl.value = newUrl
    }

    fun signOut() {
        auth.signOut()
    }
}
