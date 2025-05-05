package com.example.book14.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.book14.viewmodels.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: String,
    viewModel: ProductViewModel = viewModel(),
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var isBuyNow by remember { mutableStateOf(false) } // Phân biệt hành động
    var quantity by remember { mutableIntStateOf(1) }

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = {
                    isBuyNow = false
                    showDialog = true
                }) {
                    Text("Thêm vào giỏ")
                }
                Button(
                    onClick = {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            isBuyNow = true
                            showDialog = true
                        } else {
                            navController.navigate("login")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Mua ngay", color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable {
                        Toast.makeText(context, "Xem ảnh chi tiết: ${state.imageUrl}", Toast.LENGTH_SHORT).show()
                    },
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(state.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("★ ${state.rating}", color = Color(0xFFFFA500))
                    Spacer(Modifier.width(8.dp))
                    Text("Đã bán ${state.sold}")
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${state.price.toInt()}đ",
                    color = Color.Red,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                if (state.discountPercent > 0) {
                    Text(
                        text = "${state.originalPrice.toInt()}đ",
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text("Tác giả: ${state.author}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("Nhà xuất bản: ${state.publisher}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(16.dp))
                Text("Đặc điểm nổi bật", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                state.description.split(". ").forEach {
                    if (it.isNotBlank()) {
                        Text("• $it", fontSize = 14.sp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddToCartDialog(
            quantity = quantity,
            onQuantityChange = { quantity = it },
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                if (isBuyNow) {
                    viewModel.selectProductForPurchase(quantity)
                    navController.navigate("payment")
                } else {
                    viewModel.addToCart(quantity)
                    Toast.makeText(context, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
fun AddToCartDialog(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) { Text("Xác nhận") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Hủy") }
        },
        title = { Text("Chọn số lượng") },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
                    Icon(Icons.Default.Remove, contentDescription = "Giảm")
                }
                Text(text = "$quantity", fontSize = 18.sp)
                IconButton(onClick = { onQuantityChange(quantity + 1) }) {
                    Icon(Icons.Default.Add, contentDescription = "Tăng")
                }
            }
        }
    )
}