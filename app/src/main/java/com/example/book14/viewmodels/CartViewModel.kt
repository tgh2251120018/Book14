package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CartItem(
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: Int
)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow(
        listOf(
            CartItem(
                id = "1",
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/n/e/nep_cu_tre_em.jpg",
                name = "Nếp Cũ - Trẻ Em Chơi",
                price = 202_002
            ),
            CartItem(
                id = "2",
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/v/i/vietnam_dialy_book.jpg",
                name = "Việt Nam - Lãnh Thổ Và Các Vùng Địa Lý",
                price = 203_651
            )
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun removeItem(item: CartItem) {
        _cartItems.value = _cartItems.value.toMutableList().also { it.remove(item) }
    }

    fun getTotalPrice(): Int {
        return _cartItems.value.sumOf { it.price }
    }
}
