package com.example.taggingeventsapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductList(
    products: List<Product>,
    onAddToCart: (Product) -> Unit,
    onAddToWishlist: (Product) -> Unit,
    onProductClick: (Product) -> Unit,
) {
    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(products) { product ->
            ProductItem(
                product = product,
                onAddToCart = onAddToCart,
                onClick = onProductClick,
                onAddToWishlist = onAddToWishlist,
            )
        }
    }
}
