package com.product.groceryapplication.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.product.groceryapplication.R
import com.product.groceryapplication.utils.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
class ProductDetailsActivity : ComponentActivity() {

    companion object {
        private const val PRODUCT_ID_EXTRA = "productId"

        fun start(context: Context, productId: Int) {
            val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(PRODUCT_ID_EXTRA, productId)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val productId = intent.getIntExtra(PRODUCT_ID_EXTRA, -1)

            if (productId > 0) {
                ProductDetailsScreen(productId)
            } else {
                // Handle invalid productId
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ProductDetailsScreen(productId: Int) {

    val viewModel = ViewModelProvider(
        LocalContext.current as ComponentActivity
    )[ProductDetailsViewModel::class.java]

    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
    }

    val productListState = viewModel.productDetails.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val product = productListState.value

    val isProductInCart = remember(productId) {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isProductInCart.value = viewModel.isProductInCart(productId)
    }

    Scaffold(
        topBar = {
            val cartItemCount by viewModel.cartItemCount.observeAsState(0)
            TopAppBarComponent(isCartEnabled = true, cartItemCount = cartItemCount)
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                if (isLoading.value) {
                    Text(text = "Loading...")
                } else {

                    product?.let {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(text = product.title)
                            Text(text = "Price: $${product.price}")
                            Text(text = "Description: ${product.description}")

                            val images = product.images
                            if (images.isNotEmpty()) {
                                ViewPager(images)
                            }


                            if (isProductInCart.value) {
                                Text(
                                    text = "Added to Cart",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Magenta
                                )
                            } else {
                                // Show "Add to Cart" button
                                Button(
                                    onClick = {
                                        viewModel.addToCart(product)
                                        isProductInCart.value = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(text = "Add to Cart")
                                }
                            }
                        }
                    } ?: run {
                        Text(text = "Failed to load product details.")
                    }
                }
            }
        }
    )
}

@Composable
fun ViewPager(images: List<String>) {
    HorizontalPagerSample(images)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerSample(images: List<String>) {
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0.5f, pageCount = {
            images.size
        })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                HorizontalPager(state = pagerState) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images[page])
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.shopping_basket),
                        contentDescription = stringResource(R.string.image_desc),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    PagerIndicator(
                        currentPage = pagerState.currentPage,
                        totalPages = images.size
                    )
                }

            }
        }
    }
}

@Composable
fun PagerIndicator(currentPage: Int, totalPages: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)
    ) {
        repeat(totalPages) { index ->
            val color = if (index == currentPage) Color.Gray else Color.LightGray
            Spacer(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = color, shape = CircleShape)
//                    .padding(4.dp,vertical = 8.dp)
            )
        }
    }
}
