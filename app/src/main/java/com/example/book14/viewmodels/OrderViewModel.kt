package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

data class OrderStatus(val label: String, val icon: ImageVector)
data class BookSuggestion(
    val id: String,
    val title: String,
    val price: String,
    val imageUrl: String
)

class OrderViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

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

    private val _suggestedBooks = MutableStateFlow<List<BookSuggestion>>(emptyList())
    val suggestedBooks: StateFlow<List<BookSuggestion>> = _suggestedBooks

    init {
        loadSuggestedBooks()
    }

    private fun loadSuggestedBooks() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("products").get().await()
                val books = snapshot.documents.mapNotNull { doc ->
                    val id = doc.getString("productId") ?: return@mapNotNull null
                    val title = doc.getString("name") ?: return@mapNotNull null
                    val price = doc.getLong("price")?.toString()?.plus("đ") ?: "0đ"
                    val imageUrl = doc.getString("imageUrl") ?: ""

                    BookSuggestion(
                        id = id,
                        title = title,
                        price = price,
                        imageUrl = imageUrl
                    )
                }.shuffled(Random(System.currentTimeMillis())) // 🔥 Xáo trộn random

                _suggestedBooks.value = books
            } catch (e: Exception) {
                _suggestedBooks.value = emptyList()
            }
        }
    }
}
