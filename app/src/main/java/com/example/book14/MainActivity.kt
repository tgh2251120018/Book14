package com.example.book14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.book14.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("category") { CategoryScreen(navController) }
        composable("orders") { OrderScreen(navController) }  // ✅ Đảm bảo đã thêm
        composable("account") { AccountScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}
