package com.example.book14.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // 🔹 Header với icon ứng dụng và nút quay lại
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF3F51B5)), // Màu xanh chủ đạo
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }

        // 🔹 Form đăng nhập
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Đăng nhập", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = "", onValueChange = {},
                placeholder = { Text("Số điện thoại", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                singleLine = true
            )

            TextField(
                value = "", onValueChange = {},
                placeholder = { Text("Mật khẩu", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                singleLine = true
            )

            Button(
                onClick = { /* TODO: Xử lý đăng nhập */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
            ) {
                Text(text = "Xác nhận", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "Chưa có tài khoản? ", color = Color.Gray)
                Text(
                    text = "Đăng ký",
                    color = Color(0xFF009688),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate("signup") }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = "Hoặc đăng nhập bằng", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("G", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }
    }
}
