package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.book14.viewmodels.OrderViewModel
import com.example.book14.viewmodels.OrderStatus
import com.example.book14.viewmodels.BookSuggestion

@Composable
fun OrderScreen(navController: NavController, viewModel: OrderViewModel = viewModel()) {
    val statuses by viewModel.orderStatuses.collectAsState()
    val suggestedBooks by viewModel.suggestedBooks.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF3F51B5))
            )

            Text(
                text = "Đơn hàng của tôi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            OrderTrackingBar(statuses)
            DeliveryIcon()
            RecommendedBooks(suggestedBooks)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            OrderBottomNavigationBar(navController)
        }
    }
}

@Composable
fun OrderTrackingBar(statuses: List<OrderStatus>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFFFF0F5), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        statuses.forEach { status ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = status.icon, contentDescription = status.label, tint = Color(0xFF3F51B5))
                Text(text = status.label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun DeliveryIcon() {
    Icon(
        imageVector = Icons.Filled.LocalShipping,
        contentDescription = "Delivery Icon",
        tint = Color.Blue,
        modifier = Modifier
            .size(80.dp)
            .padding(16.dp)
    )
}

@Composable
fun RecommendedBooks(suggestedBooks: List<BookSuggestion>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bạn có thể thích", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            suggestedBooks.forEach { book ->
                BookItem(book)
            }
        }
    }
}

@Composable
fun BookItem(book: BookSuggestion) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Book,
            contentDescription = "Book Placeholder",
            tint = Color.Gray,
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = book.title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Text(text = book.price, fontSize = 12.sp, color = Color.Red, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OrderBottomNavigationBar(navController: NavController) {
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
                onClick = {
                    if (route != navController.currentDestination?.route) {
                        navController.navigate(route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    val navController = rememberNavController()
    OrderScreen(navController)
}
