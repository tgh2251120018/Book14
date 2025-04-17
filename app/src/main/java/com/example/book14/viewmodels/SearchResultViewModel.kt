package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchResultViewModel : ViewModel() {

    private val _selectedFilter = MutableStateFlow("Phổ biến")
    val selectedFilter: StateFlow<String> = _selectedFilter

    private val _showPriceFilter = MutableStateFlow(false)
    val showPriceFilter: StateFlow<Boolean> = _showPriceFilter

    fun onFilterSelected(filter: String) {
        if (filter == "Giá") {
            _showPriceFilter.value = !_showPriceFilter.value
        } else {
            _selectedFilter.value = filter
            _showPriceFilter.value = false
        }
    }

    fun onPriceSortSelected(order: String) {
        _selectedFilter.value = order
        _showPriceFilter.value = false
    }

    val productList = listOf(
        Product("1", "Boxset Harry Potter", 1571501, "https://example.com/harry1.jpg", 86),
        Product("2", "Harry Potter - Chiếc Cốc Lửa", 885500, "https://example.com/harry2.jpg", 20),
        Product("3", "Harry Potter - Tập 3", 765000, "https://example.com/harry3.jpg", 42),
        Product("4", "Harry Potter - Tập 2", 590000, "https://example.com/harry4.jpg", 30)
    )
}

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val sold: Int
)
