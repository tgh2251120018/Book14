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
import com.example.book14.ui.viewmodels.AccountViewModel
import com.example.book14.ui.viewmodels.CartViewModel
import com.example.book14.ui.viewmodels.CategoryListViewModel
import com.example.book14.ui.viewmodels.CategoryViewModel
import com.example.book14.ui.viewmodels.HomeViewModel
import com.example.book14.ui.viewmodels.LoginViewModel
import com.example.book14.ui.viewmodels.OrderViewModel
import com.example.book14.ui.viewmodels.SearchResultViewModel
import com.example.book14.ui.viewmodels.SearchViewModel
import com.example.book14.ui.viewmodels.SignUpViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel // Khai báo ViewModel tại đây để sử dụng chung

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    // Đăng nhập thành công, lưu thông tin người dùng
                    val user = auth.currentUser
                    loginViewModel.saveUserToFirestore(
                        userId = user?.uid ?: "",
                        email = user?.email,
                        displayName = user?.displayName
                    )
                    // Điều hướng về màn hình chính sau khi đăng nhập thành công
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("navigateTo", "home")
                    }
                    startActivity(intent)
                    finish() // Kết thúc activity hiện tại để tránh quay lại màn hình đăng nhập
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
        loginViewModel = LoginViewModel() // Khởi tạo ViewModel một lần duy nhất

        // Kiểm tra trạng thái đăng nhập ban đầu
        val startDestination = if (auth.currentUser != null) {
            "home" // Nếu đã đăng nhập, đi thẳng đến home
        } else {
            intent.getStringExtra("navigateTo") ?: "home" // Nếu không, kiểm tra intent hoặc mặc định là home
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController, ::startGoogleSignIn, startDestination)
        }
    }

    private fun startGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_WEB_CLIENT_ID") // Thay bằng Web client ID từ Firebase Console
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
fun AppNavigation(navController: NavHostController, startGoogleSignIn: () -> Unit = {}, startDestination: String = "home") {
    NavHost(navController, startDestination = startDestination) {
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
            LoginScreen(navController, viewModel, startGoogleSignIn)
        }
        composable("signup") {
            val viewModel: SignUpViewModel = viewModel()
            SignUpScreen(navController, viewModel, startGoogleSignIn)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}