package com.example.book14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.book14.viewmodels.AccountSettingItemData
import com.example.book14.viewmodels.AccountViewModel

@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    val settings by viewModel.settingItems.collectAsState()
    val username by viewModel.username.collectAsState()
    val avatarUrl by viewModel.avatarUrl.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsernameFromFirebase()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 🔹 Phần nền xanh
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF3F51B5))
            )

            AccountHeader(navController, username, avatarUrl, viewModel)
            AccountSettingsList(settings)
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

@Composable
fun AccountHeader(navController: NavController, username: String, avatarUrl: String, viewModel: AccountViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    if (viewModel.isUserLoggedIn()) {
                        navController.navigate("account_detail")
                    } else {
                        navController.navigate("login")
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (avatarUrl.isNotEmpty()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(50.dp),
                    tint = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Chào mừng $username!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AccountSettingsList(settings: List<AccountSettingItemData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        settings.forEach { item ->
            AccountSettingItem(title = item.title, icon = item.icon)
        }
    }
}

@Composable
fun AccountSettingItem(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF3F51B5))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AccountNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF3F51B5),
        contentColor = Color.White,
        modifier = Modifier
            .shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        val items = listOf(
            Triple("Trang chủ", Icons.Filled.Home, "home"),
            Triple("Danh mục", Icons.AutoMirrored.Filled.List, "category"),
            Triple("Đơn hàng", Icons.Filled.ShoppingCart, "orders"),
            Triple("Tài khoản", Icons.Filled.Person, "account")
        )

        items.forEach { (label, icon, route) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (route == "account") Color(0xFFFFD700) else Color.White
                    )
                },
                label = { Text(text = label, color = Color.White) },
                selected = route == "account",
                onClick = { navController.navigate(route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountScreen() {
    val navController = rememberNavController()
    AccountScreen(navController)
}
