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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.taggingeventsapp.CheckoutScreen

import com.example.taggingeventsapp.analytics.CurrencyAnalyticsHandler
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            viewModel.initAnalytics(FirebaseAnalytics.getInstance(this))
            val products by viewModel.products.collectAsState()
            val cart by viewModel.cart.collectAsState()
            var showCheckout by remember { mutableStateOf(false) }
            var showCart by remember { mutableStateOf(false) }
            var showUserDialog by remember { mutableStateOf(true) }
            val userName = viewModel.userName
            val country = viewModel.selectedCountry

            if (showUserDialog) {
                UserSetupDialog { name, selected ->
                    viewModel.setUserInfo(name, selected)
                    viewModel.setUserNameAnalytics(name)
                    viewModel.loginEvent()
                    showUserDialog = false
                }
            }

            when (country?.code) {
                "BR" -> {
                    viewModel.setBrazilianNationality(); viewModel.setRealCurrency();
                }

                "US" -> {
                    viewModel.setAmericanNationality(); viewModel.setDollarCurrency()
                }

                "FR" -> {
                    viewModel.setFrenchNationality(); viewModel.setEuroCurrency()
                }

                "DE" -> {
                    viewModel.setGermanNationality(); viewModel.setEuroCurrency()
                }

                "JP" -> {
                    viewModel.setJapaneseNationality(); viewModel.setYenCurrency()
                }

                "IT" -> {
                    viewModel.setItalianNationality(); viewModel.setEuroCurrency()
                }

                "CA" -> {
                    viewModel.setCanadianNationality(); viewModel.setCanadianDollarCurrency()
                }

                "MX" -> {
                    viewModel.setMexicanNationality(); viewModel.setMexicanPesoCurrency()
                }

                "AR" -> {
                    viewModel.setArgentineNationality(); viewModel.setArgentinePesoCurrency()
                }

                "IN" -> {
                    viewModel.setIndianNationality(); viewModel.setRupeeCurrency()
                }
            }

            viewModel.logViewItemList(products)
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

                Text("Lojinha Natura Analytics", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { showCart = !showCart }) {
                    if (showCart)
                        viewModel.logViewCart(cart)
                    Text(if (showCart) {
                        "Ver catálogo"
                    }
                    else "Ver carrinho (${cart.size})")
                }

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    showCheckout -> {
                        CheckoutScreen(
                            cart = cart,
                            onConfirm = { address, payment ->
                              //  viewModel.completePurchase(cart, address, payment)
                                showCheckout = false
                                showCart = false
                                Toast.makeText(
                                    this@MainActivity,
                                    "Compra finalizada com sucesso!",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            onBack = {
                                showCheckout = false
                            },
                            viewModel = viewModel
                        )
                    }

                    showCart -> {
                        if (cart.isEmpty()) {
                            Text("Carrinho vazio.")
                        } else {
                            CartList(cart) { product ->
                                viewModel.removeFromCart(product)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = {
                                viewModel.logBeginCheckout(cart)
                                showCheckout = true
                            }) {
                                Text("Fazer checkout")
                            }
                        }
                    }

                    else -> {
                        ProductList(
                            products = products,
                            onAddToCart = { product ->
                                viewModel.addToCart(product)
                                Toast.makeText(
                                    this@MainActivity,
                                    "${product.name} adicionado ao carrinho",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onProductClick = { product ->
                                viewModel.logProductSelected(product)
                            },
                            onAddToWishlist = { product ->
                                if (viewModel.clickWishlistButton(product)) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "${product.name} adicionado à lista de desejos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "${product.name} removido da lista de desejos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }

                }

            }
        }
    }
