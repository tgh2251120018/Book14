package com.example.book14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.NewReleases
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        // 🔹 Phần nền phía trên (màu xanh đậm, cong, ngắn hơn)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF3F51B5), Color(0xFF1A237E))
                    ),
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Thanh tìm kiếm (đưa lên trên cùng)
            SearchBar(navController)

            // 🔹 Thông tin mới nhất từ ViewModel (nằm trong phần màu xanh)
            LatestInfoSection(info = "Sách cũ mà mới\n🔥 Giảm giá 70%! 🔥")

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 SÁCH GIẢM GIÁ 50% (dưới LatestInfoSection)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF3F51B5), Color(0xFF7986CB))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                PromotionBooks()
            }

            Spacer(modifier = Modifier.height(12.dp))

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SearchBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-30).dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { navController.navigate("search") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = "Search",
            tint = Color(0xFF3F51B5),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Bạn tìm kiếm gì?",
            color = Color(0xFF757575),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Filled.ShoppingCart,
            contentDescription = "Cart",
            tint = Color(0xFF3F51B5),
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.navigate("cart") }
        )
    }
}

@Composable
fun PromotionBooks() {
    Text(
        text = "SÁCH GIẢM GIÁ 50%",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BookCategories() {
    val categories = listOf(
        "Top deal" to Icons.Outlined.ThumbUp,
        "Giảm giá" to Icons.Outlined.AttachMoney,
        "Ngoại ngữ" to Icons.AutoMirrored.Outlined.MenuBook,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), // Thêm padding ngang cho hàng
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (label, icon) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f) // Chia đều không gian cho mỗi nút
                            .padding(vertical = 8.dp) // Khoảng cách dọc giữa các hàng
                            .clickable {}
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            modifier = Modifier
                                .size(60.dp) // Tăng kích thước icon
                                .shadow(4.dp, RoundedCornerShape(12.dp))
                                .background(
                                    Color(0xFFE0E7FF),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp), // Tăng padding bên trong icon
                            tint = Color(0xFF3F51B5)
                        )
                        Spacer(modifier = Modifier.height(6.dp)) // Tăng khoảng cách giữa icon và text
                        Text(
                            text = label,
                            fontSize = 12.sp, // Giảm kích thước chữ để cân đối
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF3F51B5),
                            modifier = Modifier.fillMaxWidth() // Đảm bảo text căn giữa
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
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(
                1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFF7986CB))
                ),
                shape = RoundedCornerShape(12.dp)
            )
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
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(
                1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFF7986CB))
                ),
                shape = RoundedCornerShape(12.dp)
            )
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
                text = "Địa chỉ: $address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3F51B5)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
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
                        tint = if (route == "home") Color(0xFFFFD700) else Color.White
                    )
                },
                label = { Text(text = label, color = Color.White) },
                selected = route == "home",
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