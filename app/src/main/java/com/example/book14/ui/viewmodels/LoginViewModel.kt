package com.example.book14.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    var errorMessage = mutableStateOf("")

    fun saveUserToFirestore(userId: String, email: String?, displayName: String?) {
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(userId).set(
                    mapOf(
                        "uid" to userId,
                        "username" to (displayName ?: "Người dùng mới"),
                        "email" to (email ?: ""),
                        "address" to "",
                        "phoneNumber" to ""
                    )
                ).await()
            } catch (e: Exception) {
                errorMessage.value = "Lỗi lưu thông tin người dùng: ${e.message}"
            }
        }
    }
}