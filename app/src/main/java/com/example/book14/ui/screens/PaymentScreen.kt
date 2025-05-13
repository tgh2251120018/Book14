package com.example.book14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.book14.viewmodels.PaymentCartItem
import com.example.book14.viewmodels.PaymentViewModel
import com.example.book14.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    paymentViewModel: PaymentViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel()
) {
    val selectedPurchase by productViewModel.selectedPurchase.collectAsState()
    val currentSelectedPurchase = selectedPurchase
    val cartItems by paymentViewModel.cartItems.collectAsState()
    val userInfo by paymentViewModel.userInfo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var paymentMethod by remember { mutableStateOf("Thanh toán khi nhận hàng") }
    var deliveryMethod by remember { mutableStateOf("Giao hàng tiêu chuẩn") }

    var showPaymentDialog by remember { mutableStateOf(false) }
    var showDeliveryDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedPurchase) {
        paymentViewModel.loadCartAndUserInfo(selectedPurchase)
    }

    val itemsToDisplay = remember(currentSelectedPurchase, cartItems) {
        if (currentSelectedPurchase != null) {
            listOf(
                PaymentCartItem(
                    productId = currentSelectedPurchase.product.productId,
                    name = currentSelectedPurchase.product.name,
                    imageUrl = currentSelectedPurchase.product.imageUrl,
                    quantity = currentSelectedPurchase.quantity,
                    price = currentSelectedPurchase.product.price
                )
            )
        } else {
            cartItems
        }
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
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tổng tiền", fontWeight = FontWeight.Bold)
                    Text("${paymentViewModel.getTotal().toInt()}đ", color = Color.Red, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (userInfo.address.isBlank() || userInfo.phone.isBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Bạn cần cập nhật địa chỉ và số điện thoại.")
                            }
                            navController.navigate("account_detail")
                        } else {
                            paymentViewModel.placeOrder(
                                paymentMethod,
                                deliveryMethod,
                                selectedPurchase,
                                onSuccess = {
                                    productViewModel.clearSelectedPurchase()
                                    navController.navigate("home") {
                                        popUpTo("cart") { inclusive = true }
                                    }
                                },
                                onFailure = {
                                    scope.launch { snackbarHostState.showSnackbar("Lỗi: $it") }
                                }
                            )
                        }
                    },
                    enabled = itemsToDisplay.isNotEmpty(),
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
                itemsToDisplay.forEach { item ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.imageUrl).crossfade(true).build(),
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.LightGray, RoundedCornerShape(4.dp))
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

            // Giao hàng
            ListItem(
                headlineContent = { Text("Phương thức giao hàng") },
                supportingContent = { Text(deliveryMethod) },
                modifier = Modifier.background(Color.White).clickable { showDeliveryDialog = true }
            )

            // Thanh toán
            ListItem(
                headlineContent = { Text("Phương thức thanh toán") },
                supportingContent = { Text(paymentMethod) },
                modifier = Modifier.background(Color.White).clickable { showPaymentDialog = true }
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
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Tổng tiền hàng")
                    Text("${paymentViewModel.getTotal().toInt()}đ")
                }
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Phí vận chuyển")
                    Text("Miễn phí", color = Color.Green)
                }
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Khuyến mãi")
                    Text("-0đ")
                }
            }
        }
    }

    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text("Chọn phương thức thanh toán") },
            text = {
                Column {
                    listOf("Thanh toán khi nhận hàng", "Chuyển khoản QR").forEach { method ->
                        Text(
                            method,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    paymentMethod = method
                                    showPaymentDialog = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }

    if (showDeliveryDialog) {
        AlertDialog(
            onDismissRequest = { showDeliveryDialog = false },
            title = { Text("Chọn phương thức giao hàng") },
            text = {
                Column {
                    listOf("Giao hàng tiêu chuẩn", "Giao hàng nhanh").forEach { method ->
                        Text(
                            method,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    deliveryMethod = method
                                    showDeliveryDialog = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }
}
