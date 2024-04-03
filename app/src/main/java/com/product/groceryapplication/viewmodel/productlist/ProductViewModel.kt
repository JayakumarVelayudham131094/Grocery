package com.product.groceryapplication.ui.productlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.product.groceryapplication.data.local.AppDatabase
import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.local.ProductDao
import com.product.groceryapplication.data.remote.ProductService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao: ProductDao = AppDatabase.getInstance(application).productDao()
    val cartItemCount: LiveData<Int> = productDao.observeCartItemCount()
    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var nextPage = 0
    private var totalProducts = 0

    init {
        fetchNextProducts()
    }

    suspend fun isProductInCart(productId: Int): Boolean {
        val product = productDao.getProductItem(productId)
        return product != null
    }

    fun fetchNextProducts() {
        if (_isLoading.value) return
        if (nextPage > 0 && _productList.value.size >= totalProducts) return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProductService.fetchProducts(nextPage)
                totalProducts = response.total
                val newProducts = response.products
                if (newProducts.isNotEmpty()) {
                    _productList.value += newProducts
                    nextPage = response.skip + response.limit
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(this.javaClass.name, it) }
            } finally {
                _isLoading.value = false
            }
        }
    }


}
