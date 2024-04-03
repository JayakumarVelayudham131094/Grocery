package com.product.groceryapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.product.groceryapplication.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(product: Product)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductItem(productId: Int): Product?

    @Query("SELECT COUNT(*) FROM products")
    fun observeCartItemCount(): LiveData<Int>

}