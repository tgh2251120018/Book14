package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E7FF))
    ) {
        // 🔹 Phần đầu giao diện có nền cong
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp) // Chiều cao phần nền cong
                .background(
                    color = Color(0xFF3F51B5), // Màu nền
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp) // Viền cong
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 🔹 Thông tin mới nhất trên nền cong
            LatestInfoSection()

            // 🔹 Thêm khoảng cách trước thanh tìm kiếm
            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Thanh tìm kiếm có icon giỏ hàng nằm trên nền cong
            SearchBar()

            // 🔹 Thêm khoảng cách để tách thanh tìm kiếm với hình sách khuyến mãi
            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Hình ảnh sách khuyến mãi nằm chồng lên viền cong
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -30.dp) // Đẩy lên để chồng lên viền cong
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                PromotionBooks()
            }

            // 🔹 Các thành phần còn lại
            BookCategories()
            SloganSection()
            ShippingAddress()
        }

        // 🔹 Navigation Bar phải ở dưới cùng
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigationBar(navController)
        }
    }
}

// 1️⃣ Thông tin mới nhất (Sửa lại font chữ)
@Composable
fun LatestInfoSection() {
    Text(
        text = "🔥 Sách cũ mà mới - Giảm giá 70%! 🔥",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold, // Chữ đậm
        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, // Chữ nghiêng
        color = Color(0xFFFFD700), // Màu vàng
        modifier = Modifier.padding(16.dp)
    )
}

// 2️⃣ Thanh tìm kiếm có icon giỏ hàng
@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Bạn tìm kiếm gì?", color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart", tint = Color.Gray)
    }
}

// 3️⃣ Hình ảnh sách trong đợt khuyến mãi
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

// 4️⃣ Danh mục phân loại sách
@Composable
fun BookCategories() {
    val categories = listOf(
        "Top deal" to Icons.Outlined.ThumbUp,
        "Giảm giá" to Icons.Outlined.AttachMoney,
        "Ngoại văn" to Icons.Outlined.MenuBook,
        "Bán chạy" to Icons.Outlined.ShoppingCart,
        "Nhà xuất bản" to Icons.Outlined.Business,
        "Khác" to Icons.Outlined.MoreHoriz,
        "Sách mới" to Icons.Outlined.NewReleases,
        "Tâm lý" to Icons.Outlined.Psychology,
        "Kinh doanh" to Icons.Outlined.Work
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
                            tint = Color(0xFF3F51B5) // Màu biểu tượng
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

// 5️⃣ Slogan có đóng khung
@Composable
fun SloganSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)) // Đóng khung
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp)) // Viền xám
            .padding(vertical = 12.dp), // Khoảng cách bên trong
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Đảm bảo - Uy tín - Chất lượng",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF3F51B5) // Màu chữ xanh đậm
        )
    }
}

// 6️⃣ Địa chỉ giao hàng có đóng khung
@Composable
fun ShippingAddress() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)) // Đóng khung
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp)) // Viền xám
            .padding(vertical = 12.dp, horizontal = 16.dp) // Khoảng cách bên trong
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
                text = "Giao đến: P.Tân Chánh Hiệp, Quận 12, TPHCM",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// 7️⃣ Thanh Navigation Bar
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF6200EE),
        contentColor = Color.White
    ) {
        val items = listOf(
            "Trang chủ" to Icons.Filled.Home,
            "Danh mục" to Icons.Filled.List,
            "Đơn hàng" to Icons.Filled.ShoppingCart,
            "Tài khoản" to Icons.Filled.Person
        )

        items.forEach { (label, icon) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = { Text(text = label) },
                selected = false,
                onClick = { /* TODO: Chuyển trang */ }
            )
        }
    }
}

// 🌟 Preview
@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}
