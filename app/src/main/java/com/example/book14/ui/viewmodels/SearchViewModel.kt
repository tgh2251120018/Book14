package com.example.book14.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    val popularSearches = listOf(
        "Sách kinh tế", "Tâm lý học", "Tiểu thuyết", "Lịch sử", "Sách thiếu nhi"
    )

    val featuredCategories = listOf(
        "Kinh tế", "Tâm lý", "Thiếu nhi",
        "Sức Khỏe", "Sách giáo khoa", "Ngoại văn",
        "NXB Kim Đồng", "NXB Trẻ"
    )
}
