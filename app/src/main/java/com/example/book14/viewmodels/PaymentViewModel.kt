package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class PaymentCartItem(
    val productId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val quantity: Int = 1,
    val price: Double = 0.0
)

data class OrderInfo(
    val address: String = "",
    val phone: String = "",
    val fullName: String = ""
)

class PaymentViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _cartItems = MutableStateFlow<List<PaymentCartItem>>(emptyList())
    val cartItems: StateFlow<List<PaymentCartItem>> = _cartItems

    private val _userInfo = MutableStateFlow(OrderInfo())
    val userInfo: StateFlow<OrderInfo> = _userInfo

    fun loadCartAndUserInfo(selectedPurchase: SelectedPurchase?) {
        val user = auth.currentUser ?: return
        viewModelScope.launch {
            try {
                if (selectedPurchase != null) {
                    // Trường hợp "Mua ngay"
                    _cartItems.value = listOf(
                        PaymentCartItem(
                            productId = selectedPurchase.product.productId,
                            name = selectedPurchase.product.name,
                            imageUrl = selectedPurchase.product.imageUrl,
                            quantity = selectedPurchase.quantity,
                            price = selectedPurchase.product.price
                        )
                    )
                } else {
                    // Trường hợp mua từ giỏ hàng
                    val cartSnapshot = db.collection("carts")
                        .whereEqualTo("uid", user.uid)
                        .get().await()

                    val items = cartSnapshot.documents.mapNotNull {
                        val productId = it.getString("productId") ?: return@mapNotNull null
                        val product = db.collection("products")
                            .whereEqualTo("productId", productId)
                            .get().await().documents.firstOrNull() ?: return@mapNotNull null

                        PaymentCartItem(
                            productId = productId,
                            name = product.getString("name") ?: "",
                            imageUrl = product.getString("imageUrl") ?: "",
                            quantity = it.getLong("quantity")?.toInt() ?: 1,
                            price = product.getDouble("discountPrice") ?: 0.0
                        )
                    }

                    _cartItems.value = items
                }

                // Load thông tin người dùng
                val userDoc = db.collection("users").document(user.uid).get().await()
                _userInfo.value = OrderInfo(
                    fullName = userDoc.getString("username") ?: "",
                    phone = userDoc.getString("phoneNumber") ?: "",
                    address = userDoc.getString("address") ?: ""
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun placeOrder(
        paymentMethod: String,
        deliveryMethod: String,
        selectedPurchase: SelectedPurchase?, // Truyền trực tiếp thay vì dùng ProductViewModel
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = auth.currentUser ?: return onFailure("Chưa đăng nhập")
        viewModelScope.launch {
            try {
                val orderData = hashMapOf(
                    "uid" to user.uid,
                    "items" to cartItems.value.map {
                        mapOf(
                            "productId" to it.productId,
                            "quantity" to it.quantity,
                            "price" to it.price
                        )
                    },
                    "address" to userInfo.value.address,
                    "phone" to userInfo.value.phone,
                    "fullName" to userInfo.value.fullName,
                    "total" to getTotal(),
                    "paymentMethod" to paymentMethod,
                    "deliveryMethod" to deliveryMethod,
                    "createdAt" to Timestamp.now()
                )

                db.collection("orders").add(orderData).await()

                if (selectedPurchase == null) {
                    // Nếu đặt hàng từ giỏ thì xóa giỏ
                    val cartDocs = db.collection("carts")
                        .whereEqualTo("uid", user.uid)
                        .get().await()

                    for (doc in cartDocs.documents) {
                        doc.reference.delete().await()
                    }
                }

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure(e.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    fun getTotal(): Double {
        return cartItems.value.sumOf { it.price * it.quantity }
    }
}