package com.product.groceryapplication.viewmodel.productlist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.data.model.ProductResponse
import com.product.groceryapplication.ui.productlist.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private lateinit var application: Application

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = ProductViewModel(application)
    }

    @Test
    fun fetchNextProducts() {
        runBlocking {
            val data = ProductResponse(
                listOf(
                    Product(
                        id = 22,
           title = "Elbow Macaroni - 400 gm",
            description = "Product details of Bake Parlor Big Elbow Macaroni - 400 gm",
            price = 14,
            discountPercentage = 15.58,
           rating = 4.57,
            stock = 146,
            brand = "Bake Parlor Big",
           category = "groceries",
            thumbnail = "https://cdn.dummyjson.com/product-images/22/thumbnail.jpg",
            images = listOf(
                "https://cdn.dummyjson.com/product-images/22/1.jpg",
                "https://cdn.dummyjson.com/product-images/22/2.jpg",
                "https://cdn.dummyjson.com/product-images/22/3.jpg"
            )

                    )
                ),1,1,1
            )

            val paginatedData = viewModel.fetchNextProducts()

            Truth.assertThat(viewModel)
        }
    }
}