package com.example.book14.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class OrderStatus(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
data class BookSuggestion(val title: String, val price: String)

class OrderViewModel : ViewModel() {

    private val _orderStatuses = MutableStateFlow(
        listOf(
            OrderStatus("Chờ thanh toán", Icons.Filled.Payment),
            OrderStatus("Chờ xử lý", Icons.Filled.Schedule),
            OrderStatus("Đang vận chuyển", Icons.Filled.LocalShipping),
            OrderStatus("Đang giao hàng", Icons.Filled.DeliveryDining),
            OrderStatus("Hoàn trả", Icons.Filled.Replay)
        )
    )
    val orderStatuses: StateFlow<List<OrderStatus>> = _orderStatuses

    private val _suggestedBooks = MutableStateFlow(
        listOf(
            BookSuggestion("Giáo trình A", "99.000đ"),
            BookSuggestion("Tâm lý học B", "79.000đ"),
            BookSuggestion("Kinh tế học C", "129.000đ")
        )
    )
    val suggestedBooks: StateFlow<List<BookSuggestion>> = _suggestedBooks
}
