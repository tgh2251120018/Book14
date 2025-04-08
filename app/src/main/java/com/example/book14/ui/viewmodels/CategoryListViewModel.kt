package com.example.book14.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.book14.model.Book
import com.example.book14.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryListViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    fun loadBooksByCategory(category: String) {
        _books.value = when (category) {
            "Kinh tế" -> listOf(
                Book("1", "Kinh tế học cơ bản", "120.000đ", R.drawable.categories),
                Book("2", "Chiến lược tài chính", "150.000đ", R.drawable.categories)
            )
            "Tâm lý" -> listOf(
                Book("3", "Tư duy nhanh và chậm", "130.000đ", R.drawable.categories),
                Book("4", "Sức mạnh của thói quen", "140.000đ", R.drawable.categories)
            )
            else -> listOf(
                Book("0", "Sách mẫu", "100.000đ", R.drawable.categories)
            )
        }
    }
}
