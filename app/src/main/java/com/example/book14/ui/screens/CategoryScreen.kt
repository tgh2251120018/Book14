package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun CategoryScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Nền trắng cho toàn bộ màn hình
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 🔹 Phần nền xanh phía trên
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Điều chỉnh chiều cao phù hợp
                    .background(Color(0xFF3F51B5)), // Màu xanh giống HomeScreen
            )

            // 🔹 Thanh tìm kiếm nằm dưới nền xanh
            CategorySearchBar(navController)

            // 🔹 Khoảng cách để căn danh mục vào giữa
            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Lưới danh mục 2x3 nằm chính giữa
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), // Đẩy xuống giữa
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryGrid()
            }
        }

        // 🔹 Navigation Bar ở dưới cùng
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            CategoryNavigationBar(navController)
        }
    }
}

// 📌 **Thanh tìm kiếm giống HomeScreen nhưng đổi tên**
@Composable
fun CategorySearchBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { navController.navigate("search") },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Bạn tìm kiếm gì?", color = Color.Gray)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Cart",
                tint = Color.Gray,
                modifier = Modifier.clickable { navController.navigate("cart") }
            )
        }
    }
}

// 📌 **Lưới danh mục 2x3**
@Composable
fun CategoryGrid() {
    val categories = listOf(
        "Kinh tế" to Icons.Filled.TrendingUp,
        "Tâm lý" to Icons.Filled.Psychology,
        "Thiếu nhi" to Icons.Filled.FamilyRestroom,
        "Ngoại ngữ" to Icons.Filled.Translate,
        "Sách giáo khoa" to Icons.Filled.School,
        "Comic - Manga" to Icons.Filled.AutoStories
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        categories.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (label, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            modifier = Modifier.size(70.dp),
                            tint = Color(0xFF3F51B5) // Màu xanh giống theme
                        )
                        Text(
                            text = label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// 📌 **Navigation Bar**
@Composable
fun CategoryNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF3F51B5),
        contentColor = Color.White
    ) {
        val items = listOf(
            Triple("Trang chủ", Icons.Filled.Home, "home"),
            Triple("Danh mục", Icons.Filled.List, "category"),
            Triple("Đơn hàng", Icons.Filled.ShoppingCart, "orders"),
            Triple("Tài khoản", Icons.Filled.Person, "account")
        )

        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = { Text(text = label) },
                selected = false,
                onClick = { navController.navigate(route) }
            )
        }
    }
}

// 🌟 **Preview**
@Preview(showBackground = true)
@Composable
fun PreviewCategoryScreen() {
    CategoryScreen(navController = rememberNavController())
}
