package com.example.book14.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _latestInfo = MutableStateFlow("🔥 Sách cũ mà mới - Giảm giá 70%! 🔥")
    val latestInfo: StateFlow<String> = _latestInfo

    private val _shippingAddress = MutableStateFlow("P.Tân Chánh Hiệp, Quận 12, TPHCM")
    val shippingAddress: StateFlow<String> = _shippingAddress

    // Có thể thêm dữ liệu danh mục, khuyến mãi, sản phẩm mới tại đây
    fun updateShippingAddress(newAddress: String) {
        viewModelScope.launch {
            _shippingAddress.emit(newAddress)
        }
    }
}