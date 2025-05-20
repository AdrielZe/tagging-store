package com.example.taggingeventsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var isOnWishlist by remember { mutableStateOf(product.isOnWishList) }

    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable { onClick(product) }
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
        Button(
            onClick = {
                isOnWishlist = !isOnWishlist
                product.isOnWishList = isOnWishlist
                onAddToWishlist(product)
            },
            modifier = Modifier.padding(top = 4.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isOnWishlist) Color.Blue else Color.LightGray
            )
        ) {
            Text("❤\uFE0F")
        }
    }
}
