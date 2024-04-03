package com.product.groceryapplication.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.product.groceryapplication.data.local.AppDatabase
import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.local.ProductDao
import com.product.groceryapplication.data.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao: ProductDao = AppDatabase.getInstance(application).productDao()
    var product: Product? = null

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _productDetails = MutableStateFlow<Product?>(null)
    val productDetails: StateFlow<Product?> = _productDetails

    val cartItemCount: LiveData<Int> = productDao.observeCartItemCount()

    suspend fun getProductById(productId: Int) {
        return getProductFromDatabaseOrApi(productId)
    }

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertOrUpdate(product)
        }
    }

    suspend fun isProductInCart(productId: Int): Boolean {
        val product = productDao.getProductItem(productId)
        return product != null
    }


    private fun getProductFromDatabaseOrApi(productId:Int) {
        if (_isLoading.value) return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProductService.fetchProductById(productId)
                _productDetails.value = response
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d(this.javaClass.name, it) }
            } finally {
                _isLoading.value = false
            }
        }
    }
}