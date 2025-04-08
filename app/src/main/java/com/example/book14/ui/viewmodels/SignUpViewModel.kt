package com.example.book14.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var phoneNumber = mutableStateOf("")
    var password = mutableStateOf("")

    fun onPhoneNumberChanged(newValue: String) {
        phoneNumber.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        password.value = newValue
    }

    fun onSignUpClicked() {
        // TODO: Gọi Firebase hoặc API xử lý đăng ký
        println("Đăng ký với số: ${phoneNumber.value}, mật khẩu: ${password.value}")
    }
}
