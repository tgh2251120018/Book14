package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AccountScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 🔹 Nền xanh phía trên
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF3F51B5)), // Màu xanh giống CategoryScreen và OrderScreen
            )

            // 🔹 Truyền navController vào AccountHeader
            AccountHeader(navController)

            // 🔹 Danh sách các mục cài đặt
            AccountSettingsList()
        }

        // 🔹 Navigation Bar
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AccountNavigationBar(navController)
        }
    }
}

// 📌 **Phần tiêu đề tài khoản**
@Composable
fun AccountHeader(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray, shape = CircleShape)
                .border(2.dp, Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier.size(50.dp),
                tint = Color.DarkGray
            )
        }

        // 🔹 Chào mừng & Đăng nhập/Đăng ký
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Chào mừng bạn!", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        // 🛠 Cập nhật: Thêm điều hướng đến màn hình đăng nhập
        Button(
            onClick = { navController.navigate("login") }, // Điều hướng đến LoginScreen
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(text = "Đăng nhập/Đăng ký", color = Color.White)
        }
    }
}

// 📌 **Danh sách cài đặt tài khoản**
@Composable
fun AccountSettingsList() {
    val settings = listOf(
        "Tài khoản & Bảo mật" to Icons.Filled.Lock,
        "Địa chỉ" to Icons.Filled.LocationOn,
        "Tài khoản/Thẻ ngân hàng" to Icons.Filled.CreditCard,
        "Trung tâm hỗ trợ" to Icons.Filled.Help,
        "Điều khoản dịch vụ" to Icons.Filled.Description,
        "Giới thiệu" to Icons.Filled.Info,
        "Yêu cầu xóa tài khoản" to Icons.Filled.Delete
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        settings.forEach { (title, icon) ->
            AccountSettingItem(title = title, icon = icon)
        }
    }
}

// 📌 **Item từng mục cài đặt**
@Composable
fun AccountSettingItem(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF3F51B5))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

// 📌 **Navigation Bar**
@Composable
fun AccountNavigationBar(navController: NavController) {
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
                onClick = {
                    if (route != navController.currentDestination?.route) {
                        navController.navigate(route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

// 🌟 **Preview**
@Preview(showBackground = true)
@Composable
fun PreviewAccountScreen() {
    val navController = rememberNavController()
    AccountScreen(navController)
}
