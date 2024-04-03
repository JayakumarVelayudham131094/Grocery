package com.product.groceryapplication.data.model

data class ProductResponse(
    val products: List<Product>,
    val total:Int,
    val skip:Int,
    val limit:Int
)
