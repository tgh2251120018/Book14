package com.example.book14.ui.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AccountSettingItemData(val title: String, val icon: ImageVector)

class AccountViewModel : ViewModel() {

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

    // 🛠 Có thể thêm hàm cập nhật username nếu bạn dùng Firebase Auth hoặc API sau này.
}
