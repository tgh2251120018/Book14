package com.example.book14.model

data class Book(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,  // sửa từ String sang Double
    val originalPrice: Double = 0.0,
    val discountPrice: Double,
    val imageUrl: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val soldQuantity: Int
)

