package com.product.groceryapplication.utils

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import com.product.groceryapplication.ui.cart.CartListActivity

@Composable
fun BadgeBox(
    count: Int,
    content: @Composable () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        content()
        if (count >= 0) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(24.dp)
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopAppBarComponent(
    cartItemCount: Int? = null,
    isCartEnabled : Boolean = false
) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = "Grocery Store") },
        actions = {
            if(isCartEnabled && cartItemCount!=null && cartItemCount>=0){
                BadgeBox(count = cartItemCount) {
                    IconButton(
                        onClick = { onCartClick(context) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.Blue
                        )
                    }
                }
            }

        }
    )
}

fun onCartClick(context: Context) {
    CartListActivity.start(context)
}