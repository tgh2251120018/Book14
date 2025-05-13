package com.example.book14.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.regex.Pattern

class SearchResultViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _selectedFilter = MutableStateFlow("")
    val selectedFilter: StateFlow<String> = _selectedFilter

    private val _priceSortAsc = MutableStateFlow(true)
    val priceSortAsc: StateFlow<Boolean> = _priceSortAsc

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults

    private var fullResults: List<Product> = emptyList()

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
        applyFilter()
    }

    fun onPriceSortSelected() {
        _priceSortAsc.value = !_priceSortAsc.value
        _selectedFilter.value = if (_priceSortAsc.value) "Giá: Thấp đến cao" else "Giá: Cao đến thấp"
        applyFilter()
    }

    fun searchProducts(keyword: String) {
        val normalizedKeyword = normalize(keyword).lowercase()

        viewModelScope.launch {
            db.collection("products")
                .get()
                .addOnSuccessListener { result ->
                    val matched = result.mapNotNull { doc ->
                        val name = doc.getString("name") ?: return@mapNotNull null
                        val normalizedName = normalize(name).lowercase()

                        if (normalizedName.contains(normalizedKeyword)) {
                            val createdAtTimestamp = try {
                                val ts = doc.get("createdAt")
                                when (ts) {
                                    is com.google.firebase.Timestamp -> ts.seconds
                                    is Map<*, *> -> {
                                        val seconds = (ts["_seconds"] as? Number)?.toLong() ?: 0L
                                        seconds
                                    }
                                    else -> 0L
                                }
                            } catch (e: Exception) {
                                0L
                            }

                            Product(
                                id = doc.getString("productId") ?: "",
                                name = name,
                                price = (doc.getDouble("price") ?: 0.0).toInt(),
                                imageUrl = doc.getString("imageUrl") ?: "",
                                sold = (doc.getLong("soldQuantity") ?: 0L).toInt(),
                                isNew = doc.getBoolean("isNew") == true,
                                isBestSeller = doc.getBoolean("isBestSeller") == true,
                                isTopDeal = doc.getBoolean("isTopDeal") == true,
                                createdAt = createdAtTimestamp
                            )
                        } else null
                    }

                    fullResults = matched
                    applyFilter()
                }
        }
    }

    private fun applyFilter() {
        val filtered = when (_selectedFilter.value) {
            "Phổ biến" -> fullResults.filter { it.isTopDeal }
            "Bán chạy" -> fullResults.filter { it.isBestSeller }
            "Mới nhất" -> fullResults.filter { it.isNew }
            "Giá: Thấp đến cao", "Giá: Cao đến thấp" -> fullResults
            else -> fullResults // Không áp dụng filter
        }

        val sortedList = when (_selectedFilter.value) {
            "Giá: Thấp đến cao" -> filtered.sortedBy { it.price }
            "Giá: Cao đến thấp" -> filtered.sortedByDescending { it.price }
            "Mới nhất" -> filtered.sortedByDescending { it.createdAt }
            else -> filtered // Không sort thêm
        }

        _searchResults.value = sortedList
    }

    private fun normalize(str: String): String {
        val temp = Normalizer.normalize(str, Normalizer.Form.NFD)
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(temp).replaceAll("")
    }
}

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val sold: Int,
    val isNew: Boolean = false,
    val isBestSeller: Boolean = false,
    val isTopDeal: Boolean = false,
    val createdAt: Long = 0L // Unix timestamp
)
