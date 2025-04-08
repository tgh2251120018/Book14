package com.example.book14.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.book14.R

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
        Product("Boxset Harry Potter", 1571501, R.drawable.categories, 86),
        Product("Harry Potter - Chiếc Cốc Lửa", 885500, R.drawable.categories, 20),
        Product("Harry Potter - Tập 3", 765000, R.drawable.categories, 42),
        Product("Harry Potter - Tập 2", 590000, R.drawable.categories, 30)
    )
}

data class Product(val name: String, val price: Int, val image: Int, val sold: Int)