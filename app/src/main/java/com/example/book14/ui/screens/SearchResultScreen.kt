package com.example.book14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.book14.viewmodels.Product
import com.example.book14.viewmodels.SearchResultViewModel

@Composable
fun SearchResultScreen(
    navController: NavController,
    query: String,
    viewModel: SearchResultViewModel = viewModel()
) {
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val priceSortAsc by viewModel.priceSortAsc.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    var searchQuery by remember { mutableStateOf(query) }

    // Tìm kiếm ban đầu khi mở màn hình
    LaunchedEffect(query) {
        viewModel.searchProducts(query)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // 🔍 Thanh tìm kiếm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Tìm kiếm...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.searchProducts(searchQuery)
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }

        // 📊 Bộ lọc
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val filters = listOf("Phổ biến", "Bán chạy", "Mới nhất", "Giá")
            filters.forEach { filter ->
                if (filter == "Giá") {
                    Button(
                        onClick = { viewModel.onPriceSortSelected() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (selectedFilter.startsWith("Giá")) Color.Red else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Giá", color = Color.White, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (priceSortAsc) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                contentDescription = "Sort",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = { viewModel.onFilterSelected(filter) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (selectedFilter == filter) Color.Red else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = filter, color = Color.White, fontSize = 14.sp)
                    }
                }
            }
        }

        // 📚 Danh sách sản phẩm
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(searchResults) { product ->
                ProductItem(product = product) {
                    navController.navigate("product/${product.id}")
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier.size(100.dp)
        )
        Text(text = product.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = "${product.price}đ", fontSize = 14.sp, color = Color.Red)
        Text(text = "Đã bán ${product.sold}", fontSize = 12.sp, color = Color.Gray)
    }
}
