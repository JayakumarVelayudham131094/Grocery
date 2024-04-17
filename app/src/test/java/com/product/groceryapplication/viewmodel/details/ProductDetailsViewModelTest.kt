package com.product.groceryapplication.viewmodel.details

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.product.groceryapplication.MainCoroutineRule
import com.product.groceryapplication.ui.details.ProductDetailsViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailsViewModelTest {

    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var application: Application

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRole = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = mockk()
        viewModel = ProductDetailsViewModel(application)
    }

    @Test
    fun getProductById() {
        getProductFromDatabaseOrApi()
    }

    private fun getProductFromDatabaseOrApi(){
        mainCoroutineRole.launch {
            viewModel.productDetails
        }
        //assert
        Truth.assertThat(viewModel)

    }

}