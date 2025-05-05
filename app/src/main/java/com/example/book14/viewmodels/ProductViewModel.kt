package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ProductUiState(
    val productId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val originalPrice: Double = 0.0,
    val discountPercent: Int = 0,
    val imageUrl: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val sold: Int = 0,
    val author: String = "",
    val publisher: String = ""
)

data class SelectedPurchase(
    val product: ProductUiState,
    val quantity: Int
)

class ProductViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    // Sản phẩm mua ngay (gồm cả số lượng)
    private val _selectedPurchase = MutableStateFlow<SelectedPurchase?>(null)
    val selectedPurchase: StateFlow<SelectedPurchase?> = _selectedPurchase

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("products")
                    .whereEqualTo("productId", productId)
                    .get()
                    .await()

                val doc = querySnapshot.documents.firstOrNull()
                if (doc != null) {
                    _uiState.value = ProductUiState(
                        productId = productId,
                        name = doc.getString("name") ?: "",
                        price = doc.getDouble("discountPrice") ?: 0.0,
                        originalPrice = doc.getDouble("price") ?: 0.0,
                        discountPercent = calculateDiscount(
                            doc.getDouble("price") ?: 0.0,
                            doc.getDouble("discountPrice") ?: 0.0
                        ),
                        imageUrl = doc.getString("imageUrl") ?: "",
                        description = doc.getString("description") ?: "",
                        rating = 4.5,
                        sold = (10..200).random(),
                        author = doc.getString("author") ?: "",
                        publisher = doc.getString("publisher") ?: ""
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun calculateDiscount(original: Double, discounted: Double): Int {
        if (original == 0.0) return 0
        return (((original - discounted) / original) * 100).toInt()
    }

    fun selectProductForPurchase(quantity: Int) {
        _selectedPurchase.value = SelectedPurchase(_uiState.value, quantity)
    }

    fun clearSelectedPurchase() {
        _selectedPurchase.value = null
    }

    fun addToCart(quantity: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModelScope.launch {
                try {
                    val cartData = hashMapOf(
                        "uid" to user.uid,
                        "productId" to _uiState.value.productId,
                        "quantity" to quantity,
                        "addedAt" to FieldValue.serverTimestamp(),
                        "cartId" to db.collection("carts").document().id
                    )
                    db.collection("carts").add(cartData).await()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
