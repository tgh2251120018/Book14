package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CategoryItem(val label: String, val icon: ImageVector, val categoryId: String)

class CategoryViewModel : ViewModel() {
    private val _categories = MutableStateFlow(
        listOf(
            CategoryItem("Kinh tế", Icons.Filled.TrendingUp, "cat001"),
            CategoryItem("Tâm lý", Icons.Filled.Psychology, "cat002"),
            CategoryItem("Thiếu nhi", Icons.Filled.FamilyRestroom, "cat003"),
            CategoryItem("Ngoại ngữ", Icons.Filled.Translate, "cat004"),
            CategoryItem("Sách giáo khoa", Icons.Filled.School, "cat005"),
            CategoryItem("Comic - Manga", Icons.Filled.AutoStories, "cat006")
        )
    )
    val categories: StateFlow<List<CategoryItem>> = _categories
}
