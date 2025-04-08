package com.example.book14.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var phoneNumber = mutableStateOf("")
    var password = mutableStateOf("")

    fun onPhoneNumberChanged(newValue: String) {
        phoneNumber.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        password.value = newValue
    }

    fun onLoginClicked() {
        // TODO: Gọi Firebase hoặc xử lý API đăng nhập
        println("Đăng nhập với số: ${phoneNumber.value}, mật khẩu: ${password.value}")
    }
}
