package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import com.example.book14.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryListViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    fun loadBooksByCategory(category: String) {
        _books.value = when (category) {
            "Kinh tế" -> listOf(
                Book("1", "Kinh tế học cơ bản", 120000.0, 150000.0, 20, "", "", 4.5, 1000),
                Book("2", "Chiến lược tài chính", 150000.0, 180000.0, 17, "", "", 4.7, 800)
            )
            "Tâm lý" -> listOf(
                Book("3", "Tư duy nhanh và chậm", 130000.0, 160000.0, 18, "", "", 4.6, 1200),
                Book("4", "Sức mạnh của thói quen", 140000.0, 170000.0, 17, "", "", 4.4, 950)
            )
            else -> listOf(
                Book("0", "Sách mẫu", 100000.0, 120000.0, 16, "", "", 4.0, 500)
            )
        }
    }
}
