package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.book14.R

data class CartItem(val imageRes: Int, val name: String, val price: Int)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow(
        listOf(
            CartItem(R.drawable.book, "Nếp Cũ - Trẻ Em Chơi", 202_002),
            CartItem(R.drawable.book, "Việt Nam - Lãnh Thổ Và Các Vùng Địa Lý", 203_651)
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
