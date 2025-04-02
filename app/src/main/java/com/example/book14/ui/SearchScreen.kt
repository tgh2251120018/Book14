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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.book14.R

@Composable
fun SearchScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // Thanh tìm kiếm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sản phẩm, thương hiệu và mọi thứ bạn cần...", color = Color.Gray)
                }
            }
        }

        // Tìm Kiếm Phổ Biến
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
            items(listOf("cân điện tử sức khỏe", "giày nam", "áo len trẻ trung nữ", "vợt cầu lông yonex", "phụ kiện giày buybox", "vú heo")) { item ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, fontSize = 14.sp)
                }
            }
        }

        // Danh Mục Nổi Bật
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
                        .fillMaxWidth(),
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
