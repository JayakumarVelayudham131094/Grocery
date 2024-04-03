package com.product.groceryapplication.data.remote

import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product
}
