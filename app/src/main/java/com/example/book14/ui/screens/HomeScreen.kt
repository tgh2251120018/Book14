package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.book14.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val latestInfo by viewModel.latestInfo.collectAsState()
    val shippingAddress by viewModel.shippingAddress.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E7FF))
    ) {
        // 🔹 Phần nền cong phía trên
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    color = Color(0xFF3F51B5),
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 🔹 Thông tin mới nhất từ ViewModel
            LatestInfoSection(info = latestInfo)

            Spacer(modifier = Modifier.height(8.dp))

            SearchBar(navController)

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-30).dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                PromotionBooks()
            }

            BookCategories()
            SloganSection()
            ShippingAddress(address = shippingAddress)
        }

        // 🔹 Navigation bar dưới cùng
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigationBar(navController)
        }
    }
}

@Composable
fun LatestInfoSection(info: String) {
    Text(
        text = info,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
        color = Color(0xFFFFD700),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SearchBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { navController.navigate("search") },
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

@Composable
fun PromotionBooks() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Blue, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(text = "SÁCH GIẢM GIÁ 50%", color = Color.White, fontSize = 18.sp)
    }
}

@Composable
fun BookCategories() {
    val categories = listOf(
        "Top deal" to Icons.Outlined.ThumbUp,
        "Giảm giá" to Icons.Outlined.AttachMoney,
        "Ngoại văn" to Icons.Outlined.MenuBook,
        "Sách mới" to Icons.Outlined.NewReleases,
        "Tâm lý" to Icons.Outlined.Psychology,
        "Kinh doanh" to Icons.Outlined.Work,
        "Bán chạy" to Icons.Outlined.ShoppingCart,
        "Nhà xuất bản" to Icons.Outlined.Business,
        "Khác" to Icons.Outlined.MoreHoriz
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        categories.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (label, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            modifier = Modifier.size(50.dp),
                            tint = Color(0xFF3F51B5)
                        )
                        Text(
                            text = label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SloganSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Đảm bảo - Uy tín - Chất lượng",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF3F51B5)
        )
    }
}

@Composable
fun ShippingAddress(address: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Giao đến: $address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
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

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}
