package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.book14.viewmodels.PaymentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController, viewModel: PaymentViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val userInfo by viewModel.userInfo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var paymentMethod by remember { mutableStateOf("Thanh toán khi nhận hàng") }
    var deliveryMethod by remember { mutableStateOf("Giao hàng tiêu chuẩn") }

    var showPaymentDialog by remember { mutableStateOf(false) }
    var showDeliveryDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCartAndUserInfo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xác nhận đơn hàng") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tổng tiền", fontWeight = FontWeight.Bold)
                    Text(
                        text = "${viewModel.getTotal().toInt()}đ",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.placeOrder(
                            onSuccess = {
                                navController.navigate("home") {
                                    popUpTo("cart") { inclusive = true }
                                }
                            },
                            onFailure = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Lỗi: $it")
                                }
                            }
                        )
                    },
                    enabled = cartItems.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Đặt hàng", color = Color.White)
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF6F6F6))
        ) {
            // Giao đến
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Giao đến", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("${userInfo.fullName} | ${userInfo.phone}")
                Text(userInfo.address)
            }

            Spacer(Modifier.height(8.dp))

            // Sản phẩm
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Sản phẩm", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                cartItems.forEach { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.SemiBold, maxLines = 2)
                            Text("Số lượng: x${item.quantity}")
                        }
                        Text("${(item.price * item.quantity).toInt()}đ", color = Color.Red)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Phương thức giao hàng
            ListItem(
                headlineContent = { Text("Phương thức giao hàng") },
                supportingContent = { Text(deliveryMethod) },
                modifier = Modifier
                    .background(Color.White)
                    .clickable { showDeliveryDialog = true }
            )

            // Phương thức thanh toán
            ListItem(
                headlineContent = { Text("Phương thức thanh toán") },
                supportingContent = { Text(paymentMethod) },
                modifier = Modifier
                    .background(Color.White)
                    .clickable { showPaymentDialog = true }
            )

            Spacer(Modifier.height(8.dp))

            // Chi tiết thanh toán
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Chi tiết thanh toán", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tổng tiền hàng")
                    Text("${viewModel.getTotal().toInt()}đ")
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Phí vận chuyển")
                    Text("Miễn phí", color = Color.Green)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Khuyến mãi")
                    Text("-0đ")
                }
            }
        }
    }

    // 🔹 Dialog chọn phương thức thanh toán
    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text("Chọn phương thức thanh toán") },
            text = {
                Column {
                    Text("Thanh toán khi nhận hàng", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            paymentMethod = "Thanh toán khi nhận hàng"
                            showPaymentDialog = false
                        }
                        .padding(8.dp)
                    )
                    Text("Chuyển khoản QR", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            paymentMethod = "Chuyển khoản QR"
                            showPaymentDialog = false
                        }
                        .padding(8.dp)
                    )
                }
            },
            confirmButton = {}
        )
    }

    // 🔹 Dialog chọn phương thức vận chuyển
    if (showDeliveryDialog) {
        AlertDialog(
            onDismissRequest = { showDeliveryDialog = false },
            title = { Text("Chọn phương thức giao hàng") },
            text = {
                Column {
                    Text("Giao hàng tiêu chuẩn", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            deliveryMethod = "Giao hàng tiêu chuẩn"
                            showDeliveryDialog = false
                        }
                        .padding(8.dp)
                    )
                    Text("Giao hàng nhanh", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            deliveryMethod = "Giao hàng nhanh"
                            showDeliveryDialog = false
                        }
                        .padding(8.dp)
                    )
                }
            },
            confirmButton = {}
        )
    }
}
