package com.example.taggingeventsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CartList(cartItems: List<Product>, onRemove: (Product) -> Unit) {
    Column {
        cartItems.forEach { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.weight(1f)) {
                    Text(product.name)
                    Text("R$ %.2f".format(product.price))
                }
                Button(onClick = { onRemove(product) }) {
                    Text("Remover")
                }
            }
        }
    }
}
