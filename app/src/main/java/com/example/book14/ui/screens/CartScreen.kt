package com.example.book14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.book14.viewmodels.CartItem
import com.example.book14.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Giỏ hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->
            if (loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    if (cartItems.isEmpty()) {
                        EmptyCartSection()
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(cartItems) { item ->
                                CartItemRow(
                                    item = item,
                                    onRemove = {
                                        viewModel.removeItem(item.cartId)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Đã xóa sản phẩm khỏi giỏ!")
                                        }
                                    },
                                    onIncrease = { viewModel.updateQuantity(item, item.quantity + 1) },
                                    onDecrease = { viewModel.updateQuantity(item, item.quantity - 1) },
                                    onClick = {
                                        navController.navigate("product/${item.productId}")
                                    }
                                )
                            }
                        }
                        CheckoutSection(viewModel.getTotalPrice(), navController)
                    }
                }
            }
        }
    )
}

@Composable
fun CartItemRow(
    item: CartItem,
    onRemove: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = item.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "${item.price}đ", fontSize = 14.sp, color = Color.Red)

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, enabled = item.quantity > 1) {
                    Icon(Icons.Filled.Remove, contentDescription = "Giảm")
                }
                Text(text = "${item.quantity}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Filled.Add, contentDescription = "Tăng")
                }
            }
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Delete, contentDescription = "Remove", tint = Color.Gray)
        }
    }
}

@Composable
fun EmptyCartSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Giỏ hàng trống", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Thêm sản phẩm vào giỏ để tiếp tục mua sắm!",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun CheckoutSection(totalPrice: Int, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tổng cộng:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "${totalPrice}đ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    navController.navigate("payment")
                } else {
                    navController.navigate("login")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
        ) {
            Text(text = "Thanh toán", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
