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
fun SearchResultScreen(navController: NavController, query: String) {
    var showPriceFilter by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Phổ biến") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // 🔹 Thanh tìm kiếm
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
                    Text(text = query, color = Color.Gray)
                }
            }
        }

        // 🔹 Thanh lọc tìm kiếm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val filters = listOf("Phổ biến", "Bán chạy", "Mới nhất", "Giá")
            filters.forEach { filter ->
                Button(
                    onClick = {
                        if (filter == "Giá") {
                            showPriceFilter = !showPriceFilter
                        } else {
                            selectedFilter = filter
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedFilter == filter) Color.Red else Color.LightGray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = filter, color = Color.White, fontSize = 14.sp)
                }
            }
        }

        // 🔹 Bộ lọc giá (nếu bấm vào "Giá")
        if (showPriceFilter) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { selectedFilter = "Giá: Thấp đến cao" }) {
                    Text("Từ thấp đến cao", fontSize = 14.sp)
                }
                Button(onClick = { selectedFilter = "Giá: Cao đến thấp" }) {
                    Text("Từ cao đến thấp", fontSize = 14.sp)
                }
            }
        }

        // 🔹 Danh sách sản phẩm (dữ liệu mẫu)
        val products = listOf(
            Product("Boxset Harry Potter", 1571501, R.drawable.categories, 86),
            Product("Harry Potter - Chiếc Cốc Lửa", 885500, R.drawable.categories, 20),
            Product("Harry Potter - Tập 3", 765000, R.drawable.categories, 42),
            Product("Harry Potter - Tập 2", 590000, R.drawable.categories, 30)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

// 🔹 Dữ liệu sản phẩm
data class Product(val name: String, val price: Int, val image: Int, val sold: Int)

@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.image),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp)
        )
        Text(text = product.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = "${product.price}đ", fontSize = 14.sp, color = Color.Red)
        Text(text = "Đã bán ${product.sold}", fontSize = 12.sp, color = Color.Gray)
    }
}
