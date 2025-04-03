package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.book14.R

@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // 🔹 Thanh tìm kiếm (Đã sửa giống HomeScreen.kt, CategoryScreen.kt)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Sản phẩm, thương hiệu và mọi thứ bạn cần...") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                if (searchText.isNotBlank()) {
                    navController.navigate("searchResult/$searchText")
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }

        // 🔹 Tìm Kiếm Phổ Biến
        Text(
            text = "🔥 Tìm Kiếm Phổ Biến",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(listOf("List")) { item ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .clickable {
                            searchText = item
                            navController.navigate("searchResult/$searchText")
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, fontSize = 14.sp)
                }
            }
        }

        // 🔹 Danh Mục Nổi Bật
        Text(
            text = "📌 Danh Mục Nổi Bật",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            val categories = listOf(
                "Kinh tế", "Tâm lý", "Thiếu nhi",
                "Sức Khỏe", "Sách giáo khoa", "Ngoại văn",
                "NXB Kim Đồng", "NXB Trẻ"
            )
            items(categories) { category ->
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                        .clickable {
                            searchText = category
                            navController.navigate("searchResult/$searchText")
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.categories), // Hình ảnh danh mục
                        contentDescription = category,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
