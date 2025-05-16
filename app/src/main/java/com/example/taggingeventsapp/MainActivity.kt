package com.example.taggingeventsapp
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.initAnalytics(FirebaseAnalytics.getInstance(this))
            val products by viewModel.products.collectAsState()
            val cart by viewModel.cart.collectAsState()
            var showCart by remember { mutableStateOf(false) }
            var showUserDialog by remember { mutableStateOf(true) }
            val userName = viewModel.userName
            val country = viewModel.selectedCountry

            if (showUserDialog) {
                UserSetupDialog { name, selected ->
                    viewModel.setUserInfo(name, selected)
                    showUserDialog = false
                }
            }

           if (country?.code == "BR")
               viewModel.BrazilianNationalityHandler()

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (userName.isNotBlank() && country != null) {
                        Text("$userName (${country.name})")
                        Spacer(Modifier.width(8.dp))
                        AsyncImage(
                            model = country.flagUrl,
                            contentDescription = country.name,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Text("Lojinha Compose", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { showCart = !showCart }) {
                    Text(if (showCart) "Ver catálogo" else "Ver carrinho (${cart.size})")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (showCart) {
                    if (cart.isEmpty()) {
                        Text("Carrinho vazio.")
                    } else {
                        CartList(cart) { product ->
                            viewModel.removeFromCart(product)
                        }

                    }
                } else {
                    ProductList(products) { product ->
                        viewModel.addToCart(product)
                        Toast.makeText(
                            this@MainActivity,
                            "${product.name} adicionado ao carrinho",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
