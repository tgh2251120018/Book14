package com.example.book14.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.book14.model.Book
import com.example.book14.ui.viewmodels.CategoryListViewModel

@Composable
fun CategoryListScreen(
    navController: NavController,
    categoryName: String,
    viewModel: CategoryListViewModel = viewModel()
) {
    // Lấy danh sách sách từ ViewModel (StateFlow)
    val books by viewModel.books.collectAsState()

    // Load danh sách theo danh mục khi màn hình hiển thị
    LaunchedEffect(categoryName) {
        viewModel.loadBooksByCategory(categoryName)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔹 Header nền xanh
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF3F51B5))
        )

        // 🔹 Thanh tìm kiếm (có thể tái sử dụng lại nếu bạn có CategorySearchBar riêng)
        CategorySearchBar(navController)

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Tiêu đề danh mục
        Text(
            text = "Danh mục: $categoryName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // 🔹 Danh sách sách
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(books) { book ->
                BookItem(book = book, onClick = {
                    navController.navigate("productDetail/${book.id}")
                })
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
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
