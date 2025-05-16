package com.example.taggingeventsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.libraries.intelligence.acceleration.Analytics
import com.google.firebase.analytics.FirebaseAnalytics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun initAnalytics(analytics: FirebaseAnalytics) {
        firebaseAnalytics = analytics
    }

    private val _products = MutableStateFlow(
        listOf(
            Product(1, "Hidratante Tododia", 59.90, "https://a-static.mlcdn.com.br/1500x1500/creme-hidratante-corporal-feminino-natura-tododia-limao-siciliano-e-flor-de-gardenia-400ml/ourway/nat-0828/41ce96bc8c9afe779a823266dd2c3935.jpeg"),
            Product(2, "Eau de Parfum Natura", 199.99, "https://production.na01.natura.com/on/demandware.static/-/Sites-natura-br-storefront-catalog/default/dwd2e88c9f/Produtos/NATBRA-%20151025_1.jpg"),
            Product(3, "Natura Homem", 39.99, "https://images-americanas.b2w.io/produtos/3847069661/imagens/perfume-masculino-natura-homem-100ml/3847069661_1_xlarge.jpg"),
            Product(4, "Natura Una Senses", 149.90, "https://http2.mlstatic.com/D_NQ_NP_780876-MLA50510738906_062022-O.webp")
        )
    )

    val products: StateFlow<List<Product>> = _products

    private val _cart = MutableStateFlow<List<Product>>(emptyList())
    val cart: StateFlow<List<Product>> = _cart


    fun addToCart(product: Product) {
        _cart.value = _cart.value + product
    }

    fun removeFromCart(product: Product) {
        _cart.value = _cart.value.toMutableList().also { it.remove(product) }
    }

    var userName by mutableStateOf("")
    var selectedCountry by mutableStateOf<Country?>(null)

    fun setUserInfo(name: String, country: com.example.taggingeventsapp.Country) {
        userName = name
        selectedCountry = country
    }

    fun BrazilianNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "brazilian")
    }

}
