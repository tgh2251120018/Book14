package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CategoryItem(val label: String, val icon: ImageVector)

class CategoryViewModel : ViewModel() {
    private val _categories = MutableStateFlow(
        listOf(
            CategoryItem("Kinh tế", Icons.Filled.TrendingUp),
            CategoryItem("Tâm lý", Icons.Filled.Psychology),
            CategoryItem("Thiếu nhi", Icons.Filled.FamilyRestroom),
            CategoryItem("Ngoại ngữ", Icons.Filled.Translate),
            CategoryItem("Sách giáo khoa", Icons.Filled.School),
            CategoryItem("Comic - Manga", Icons.Filled.AutoStories)
        )
    )
    val categories: StateFlow<List<CategoryItem>> = _categories
}
