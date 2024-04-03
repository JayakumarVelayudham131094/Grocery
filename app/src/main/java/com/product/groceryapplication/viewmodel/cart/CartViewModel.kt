package com.product.groceryapplication.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.product.groceryapplication.data.local.AppDatabase
import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.local.ProductDao

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao: ProductDao = AppDatabase.getInstance(application).productDao()

    val productItems: LiveData<List<Product>> = productDao.getAllProducts()

}
