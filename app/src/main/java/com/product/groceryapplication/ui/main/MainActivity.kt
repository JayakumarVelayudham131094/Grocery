package com.product.groceryapplication.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.product.groceryapplication.data.model.Product
import com.product.groceryapplication.ui.details.ProductDetailsActivity
import com.product.groceryapplication.ui.productlist.ProductListScreen
import com.product.groceryapplication.ui.productlist.ProductViewModel
import com.product.groceryapplication.ui.theme.GroceryApplicationTheme
import com.product.groceryapplication.utils.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductApp()
                }
            }
        }
    }
}

fun onProductClick(context: Context, product: Product) {
    ProductDetailsActivity.start(context, product.id)
}

@ExperimentalMaterial3Api
@Composable
fun ProductApp() {
    GroceryApplicationTheme {
        // Create a NavController
        val navController = rememberNavController()

        val viewModel = ViewModelProvider(
            LocalContext.current as ComponentActivity
        )[ProductViewModel::class.java]
        val productListState = viewModel.productList.collectAsState()
        val isLoading = viewModel.isLoading.collectAsState()
        val context = LocalContext.current

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Scaffold(
                topBar = {
                    val cartItemCount by viewModel.cartItemCount.observeAsState(0)
                    TopAppBarComponent(isCartEnabled = true, cartItemCount = cartItemCount)
                },
                content = { innerPadding ->
//                    NavHost(navController = navController, startDestination = "cartList") {
//                        composable("cartList") {
//                            ProductListScreen(navController)
//                        }
//                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        ProductListScreen(
                            productList = productListState.value,
                            onProductClick =  { product -> onProductClick(context,product) },
                            onEndReached = viewModel::fetchNextProducts
                        )
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            )



        }
    }
}

