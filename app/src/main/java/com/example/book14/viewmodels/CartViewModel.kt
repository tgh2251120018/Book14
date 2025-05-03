package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class CartItem(
    val cartId: String = "",
    val productId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val price: Int = 0,
    val quantity: Int = 1
)

class CartViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val loading = MutableStateFlow(true)

    init {
        loadCart()
    }

    private fun loadCart() {
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    loading.value = true
                    val snapshot = db.collection("carts")
                        .whereEqualTo("uid", user.uid)
                        .get()
                        .await()

                    val items = snapshot.documents.mapNotNull { doc ->
                        val productId = doc.getString("productId") ?: return@mapNotNull null
                        val quantity = doc.getLong("quantity")?.toInt() ?: 1
                        val cartId = doc.id

                        val productSnapshot = db.collection("products")
                            .whereEqualTo("productId", productId)
                            .get()
                            .await()
                            .documents
                            .firstOrNull()

                        if (productSnapshot != null) {
                            CartItem(
                                cartId = cartId,
                                productId = productId,
                                name = productSnapshot.getString("name") ?: "",
                                imageUrl = productSnapshot.getString("imageUrl") ?: "",
                                price = productSnapshot.getDouble("discountPrice")?.toInt() ?: 0,
                                quantity = quantity
                            )
                        } else null
                    }

                    _cartItems.value = items
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    loading.value = false
                }
            }
        }
    }

    fun removeItem(cartId: String) {
        viewModelScope.launch {
            try {
                db.collection("carts").document(cartId).delete().await()
                loadCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) return
        viewModelScope.launch {
            try {
                db.collection("carts").document(item.cartId).update(
                    "quantity", newQuantity
                ).await()
                loadCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTotalPrice(): Int {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}
