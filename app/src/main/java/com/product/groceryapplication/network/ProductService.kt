package com.product.groceryapplication.data.remote

import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ProductApiService::class.java)

    suspend fun fetchProducts(skip: Int, limit: Int = 10): ProductResponse {
        var productResponse = ProductResponse(arrayListOf(),0,0,10)
        val responseData = service.getProducts(skip, limit)
        if(responseData.products.isNotEmpty()){
            productResponse =  responseData
        }
        return productResponse
    }

    suspend fun fetchProductById(productId: Int): Product? {
        var product: Product? = null
        val responseData = service.getProductById(productId)
        if(responseData.id>0){
            product =  responseData
        }
        return product
    }
}
