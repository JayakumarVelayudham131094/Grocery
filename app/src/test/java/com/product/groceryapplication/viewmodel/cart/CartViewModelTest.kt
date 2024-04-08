package com.product.groceryapplication.viewmodel.cart

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.product.groceryapplication.MainCoroutineRule
import com.product.groceryapplication.ui.cart.CartViewModel
import com.product.groceryapplication.ui.details.ProductDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeEach

class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var application: Application

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRole = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = CartViewModel(application)
    }

    @Test
    fun getProductItems() {
        mainCoroutineRole.launch {
            viewModel.productItems
        }
        //assert
        Truth.assertThat(viewModel)
    }
}