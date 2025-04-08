package com.example.book14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.book14.ui.screens.AccountScreen
import com.example.book14.ui.screens.CartScreen
import com.example.book14.ui.screens.CategoryListScreen
import com.example.book14.ui.screens.CategoryScreen
import com.example.book14.ui.screens.HomeScreen
import com.example.book14.ui.screens.LoginScreen
import com.example.book14.ui.screens.OrderScreen
import com.example.book14.ui.screens.SearchResultScreen
import com.example.book14.ui.screens.SearchScreen
import com.example.book14.ui.screens.SignUpScreen
import com.example.book14.ui.viewmodels.LoginViewModel
import com.example.book14.ui.viewmodels.SignUpViewModel
import com.example.book14.ui.viewmodels.AccountViewModel
import com.example.book14.ui.viewmodels.CartViewModel
import com.example.book14.ui.viewmodels.CategoryListViewModel
import com.example.book14.ui.viewmodels.CategoryViewModel
import com.example.book14.ui.viewmodels.HomeViewModel
import com.example.book14.ui.viewmodels.OrderViewModel
import com.example.book14.ui.viewmodels.SearchResultViewModel
import com.example.book14.ui.viewmodels.SearchViewModel

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
        composable("home") {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(navController, viewModel)
        }
        composable("category") {
            val viewModel: CategoryViewModel = viewModel()
            CategoryScreen(navController, viewModel)
        }
        composable("category_list/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Danh mục"
            val viewModel: CategoryListViewModel = viewModel()
            CategoryListScreen(navController, categoryName, viewModel)
        }
        composable("search") {
            val viewModel: SearchViewModel = viewModel()
            SearchScreen(navController, viewModel)
        }
        composable("cart") {
            val viewModel: CartViewModel = viewModel()
            CartScreen(navController, viewModel)
        }
        composable("searchResult/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            val viewModel: SearchResultViewModel = viewModel()
            SearchResultScreen(navController, query, viewModel)
        }
        composable("orders") {
            val viewModel: OrderViewModel = viewModel()
            OrderScreen(navController, viewModel)
        }
        composable("account") {
            val viewModel: AccountViewModel = viewModel()
            AccountScreen(navController, viewModel)
        }
        composable("login") {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(navController, viewModel)
        }
        composable("signup") {
            val viewModel: SignUpViewModel = viewModel()
            SignUpScreen(navController, viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}