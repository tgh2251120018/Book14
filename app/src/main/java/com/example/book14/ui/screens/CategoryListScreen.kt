package com.example.book14.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CategoryListScreen(navController: NavController, categoryName: String) {
    val books = getBooksByCategory(categoryName) // Lấy danh sách sách theo danh mục

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF3F51B5))
        )

        // 🔹 Thanh tìm kiếm (giống CategorySearchBar)
        CategorySearchBar(navController)

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Hiển thị tiêu đề danh mục
        Text(
            text = "Danh mục: $categoryName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // 🔹 Hiển thị danh sách sách trong danh mục
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(books) { book ->
                BookItem(book)
            }
        }
    }
}

// 📌 **Dữ liệu sách (tĩnh)**
data class Book(val title: String, val price: String, val imageRes: Int)

fun getBooksByCategory(category: String): List<Book> {
    return when (category) {
        "Kinh tế" -> listOf(
            Book("Kinh tế học cơ bản", "120.000đ", android.R.drawable.ic_menu_gallery),
            Book("Chiến lược tài chính", "150.000đ", android.R.drawable.ic_menu_gallery)
        )
        "Tâm lý" -> listOf(
            Book("Tư duy nhanh và chậm", "130.000đ", android.R.drawable.ic_menu_gallery),
            Book("Sức mạnh của thói quen", "140.000đ", android.R.drawable.ic_menu_gallery)
        )
        else -> listOf(Book("Sách mẫu", "100.000đ", android.R.drawable.ic_menu_gallery))
    }
}

// 📌 **Hiển thị một quyển sách**
@Composable
fun BookItem(book: Book) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = book.imageRes),
            contentDescription = book.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = book.price, color = Color.Red, fontSize = 14.sp)
        }
    }
}
