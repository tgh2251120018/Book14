package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchResultViewModel : ViewModel() {

    private val _selectedFilter = MutableStateFlow("Phổ biến")
    val selectedFilter: StateFlow<String> = _selectedFilter

    private val _priceSortAsc = MutableStateFlow(true)
    val priceSortAsc: StateFlow<Boolean> = _priceSortAsc

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
    }

    fun onPriceSortSelected() {
        _priceSortAsc.value = !_priceSortAsc.value
        _selectedFilter.value = if (_priceSortAsc.value) "Giá: Thấp đến cao" else "Giá: Cao đến thấp"
    }

    private val _productList = listOf(
        Product("1", "Boxset Harry Potter", 1571501, "https://example.com/harry1.jpg", 86),
        Product("2", "Harry Potter - Chiếc Cốc Lửa", 885500, "https://example.com/harry2.jpg", 20),
        Product("3", "Harry Potter - Tập 3", 765000, "https://example.com/harry3.jpg", 42),
        Product("4", "Harry Potter - Tập 2", 590000, "https://example.com/harry4.jpg", 30)
    )

    val productList: List<Product>
        get() = when {
            _selectedFilter.value.contains("Giá") ->
                if (_priceSortAsc.value) _productList.sortedBy { it.price }
                else _productList.sortedByDescending { it.price }
            else -> _productList // TODO: Add sorting for other filters if needed
        }
}

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val sold: Int
)
