package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book14.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryListViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val db = FirebaseFirestore.getInstance()

    fun loadBooksByCategory(categoryId: String) {
        viewModelScope.launch {
            db.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .addOnSuccessListener { result ->
                    val bookList = result.map { doc ->
                        Book(
                            id = doc.getString("productId") ?: "",
                            name = doc.getString("name") ?: "",
                            discountPrice = doc.getDouble("discountPrice") ?: 0.0,
                            price = doc.getDouble("price") ?: 0.0,

                            imageUrl = doc.getString("imageUrl") ?: "",
                            description = doc.getString("description") ?: "",
                            rating = 0.0, // Nếu có trường rating thì đọc thêm
                            soldQuantity = 0 // Nếu có trường soldQuantity thì đọc thêm
                        )
                    }
                    _books.value = bookList
                }
        }
    }

    private fun calculateDiscount(original: Double, discount: Double): Int {
        return if (original > 0) ((1 - (discount / original)) * 100).toInt() else 0
    }
}
