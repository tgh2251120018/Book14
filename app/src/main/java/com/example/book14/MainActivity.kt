@file:Suppress("DEPRECATION")

package com.example.book14

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.book14.ui.screens.*
import com.example.book14.viewmodels.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    val user = auth.currentUser
                    loginViewModel.saveUserToFirestore(
                        userId = user?.uid ?: "",
                        email = user?.email,
                        displayName = user?.displayName
                    )
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("navigateTo", "home")
                    }
                    startActivity(intent)
                    finish()
                } else {
                    loginViewModel.errorMessage.value = "Đăng nhập thất bại"
                }
            }
        } catch (e: Exception) {
            loginViewModel.errorMessage.value = "Lỗi: ${e.message}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        loginViewModel = LoginViewModel()

        val startDestination = if (auth.currentUser != null) {
            "home"
        } else {
            intent.getStringExtra("navigateTo") ?: "home"
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController, ::startGoogleSignIn, startDestination)
        }
    }

    private fun startGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("938659721356-avl7c5409lt31m970f985sp96al965lo.apps.googleusercontent.com") // Tạo string resource nếu cần
            // Replace with real Web Client ID
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    startGoogleSignIn: () -> Unit = {},
    startDestination: String = "home"
) {
    NavHost(navController, startDestination = startDestination) {
        composable("home") {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(navController, viewModel)
        }
        composable("category") {
            val viewModel: CategoryViewModel = viewModel()
            CategoryScreen(navController, viewModel)
        }
        composable("categoryList/{categoryName}/{categoryId}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryListScreen(navController, categoryName, categoryId)
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
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("cart") },
                navController = navController
            )
        }
        composable("payment") {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val productViewModel: ProductViewModel = viewModel()
                PaymentScreen(navController, productViewModel = productViewModel)
            } else {
                navController.navigate("login")
            }
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
            LoginScreen(navController, viewModel, startGoogleSignIn)
        }
        composable("signup") {
            val viewModel: SignUpViewModel = viewModel()
            SignUpScreen(navController, viewModel, startGoogleSignIn)
        }
        composable("account_detail") {
            val viewModel: AccountDetailViewModel = viewModel()
            AccountDetailScreen(navController, viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}
