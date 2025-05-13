package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun String.removeDiacritics(): String {
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val normalized = java.text.Normalizer.normalize(this, java.text.Normalizer.Form.NFD)
        return regex.replace(normalized, "")
    }

    val popularSearches = listOf(
        "Đắc nhân tâm", "Tâm lý học", "Sách giáo khoa", "Lịch sử", "Sách thiếu nhi"
    )

    val featuredCategories = listOf(
        "Kinh tế", "Tâm lý", "Thiếu nhi",
        "Comic", "Sách giáo khoa", "Ngoại ngữ"
    )
}
