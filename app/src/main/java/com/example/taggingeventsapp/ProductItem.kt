package com.example.taggingeventsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: (Product) -> Unit,
    onAddToWishlist: (Product) -> Unit,
    onClick: (Product) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable{onClick(product)}
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(product.name, fontSize = 18.sp, modifier = Modifier.padding(top = 4.dp))
        Text("R$ %.2f".format(product.price))
        Button(onClick = { onAddToCart(product) }, modifier = Modifier.padding(top = 4.dp)) {
            Text("Adicionar")
        }
        Button(onClick = { onAddToWishlist(product) }, modifier = Modifier.padding(top = 4.dp)) {
            Text("❤\uFE0F")
        }
    }
}
