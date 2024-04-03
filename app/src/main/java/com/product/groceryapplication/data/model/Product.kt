package com.product.groceryapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.product.groceryapplication.data.local.Converters

@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    @ColumnInfo(name = "discount_percentage") val discountPercentage: Double,
    @PrimaryKey val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)