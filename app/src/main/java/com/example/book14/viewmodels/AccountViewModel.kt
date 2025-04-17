package com.example.book14.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AccountSettingItemData(val title: String, val icon: ImageVector)

class AccountViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _username = MutableStateFlow("bạn")
    val username: StateFlow<String> = _username

    private val _settingItems = MutableStateFlow(
        listOf(
            AccountSettingItemData("Tài khoản & Bảo mật", Icons.Filled.Lock),
            AccountSettingItemData("Địa chỉ", Icons.Filled.LocationOn),
            AccountSettingItemData("Tài khoản/Thẻ ngân hàng", Icons.Filled.CreditCard),
            AccountSettingItemData("Trung tâm hỗ trợ", Icons.Filled.Help),
            AccountSettingItemData("Điều khoản dịch vụ", Icons.Filled.Description),
            AccountSettingItemData("Giới thiệu", Icons.Filled.Info),
            AccountSettingItemData("Yêu cầu xóa tài khoản", Icons.Filled.Delete)
        )
    )
    val settingItems: StateFlow<List<AccountSettingItemData>> = _settingItems

    fun loadUsernameFromFirebase() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    val snapshot = db.collection("users").document(user.uid).get().await()
                    val name = snapshot.getString("username") ?: "bạn"
                    _username.value = name
                } catch (e: Exception) {
                    _username.value = "bạn"
                }
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
