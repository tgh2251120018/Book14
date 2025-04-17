package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class OrderStatus(val label: String, val icon: ImageVector)
data class BookSuggestion(
    val id: String,
    val title: String,
    val price: String,
    val imageUrl: String
)

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
            BookSuggestion(
                id = "1",
                title = "Giáo trình A",
                price = "99.000đ",
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/g/i/giaotrinh_a.jpg"
            ),
            BookSuggestion(
                id = "2",
                title = "Tâm lý học B",
                price = "79.000đ",
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/t/a/tamlyhoc_b.jpg"
            ),
            BookSuggestion(
                id = "3",
                title = "Kinh tế học C",
                price = "129.000đ",
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/k/i/kinhtehoc_c.jpg"
            )
        )
    )
    val suggestedBooks: StateFlow<List<BookSuggestion>> = _suggestedBooks
}
