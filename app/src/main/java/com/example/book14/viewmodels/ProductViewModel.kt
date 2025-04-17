package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book14.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Book())
    val uiState: StateFlow<Book> = _uiState

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            // Tạm thời dùng dữ liệu giả. Có thể thay bằng gọi API/Firebase.
            _uiState.value = Book(
                id = productId,
                name = "Việt Nam - Lãnh Thổ Và Các Vùng Địa Lý",
                price = 203652.0,
                originalPrice = 260000.0,
                discountPercent = 22,
                imageUrl = "https://cdn0.fahasa.com/media/catalog/product/v/i/viet-nam---lanh-tho-va-cac-vung-dia-ly_1.jpg",
                description = "Cung cấp thông tin chi tiết về địa lý, khí hậu và văn hóa Việt Nam. Ngôn ngữ dễ hiểu và phù hợp với mọi độ tuổi. Giới thiệu về lịch sử chống xâm lược và phục hồi đất nước.",
                rating = 4.7,
                sold = 20
            )
        }
    }
}
