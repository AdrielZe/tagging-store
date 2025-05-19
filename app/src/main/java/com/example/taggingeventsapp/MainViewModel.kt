package com.example.taggingeventsapp

import android.os.Bundle
import androidx.collection.emptyLongSet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.taggingeventsapp.analytics.CurrencyAnalyticsHandler
import com.google.android.libraries.intelligence.acceleration.Analytics
import com.google.firebase.analytics.FirebaseAnalytics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.reflect.Array
import kotlin.collections.map
import kotlin.collections.sumOf

class MainViewModel : ViewModel() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var nationalityAnalyticsHandler: NationalityAnalyticsHandler
    private lateinit var currencyAnalyticsHandler: CurrencyAnalyticsHandler



    fun initAnalytics(analytics: FirebaseAnalytics) {
        firebaseAnalytics = analytics
        nationalityAnalyticsHandler = NationalityAnalyticsHandler(firebaseAnalytics)
        currencyAnalyticsHandler = CurrencyAnalyticsHandler(firebaseAnalytics)
    }

    private val _products = MutableStateFlow(
        listOf(
            Product(1, "Hidratante Tododia", 59.90, "https://a-static.mlcdn.com.br/1500x1500/creme-hidratante-corporal-feminino-natura-tododia-limao-siciliano-e-flor-de-gardenia-400ml/ourway/nat-0828/41ce96bc8c9afe779a823266dd2c3935.jpeg", "perfum", false),
            Product(2, "Eau de Parfum Natura", 199.99, "https://production.na01.natura.com/on/demandware.static/-/Sites-natura-br-storefront-catalog/default/dwd2e88c9f/Produtos/NATBRA-%20151025_1.jpg", "perfum", false),
            Product(3, "Natura Homem", 39.99, "https://images-americanas.b2w.io/produtos/3847069661/imagens/perfume-masculino-natura-homem-100ml/3847069661_1_xlarge.jpg", "perfum", false),
            Product(4, "Natura Una Senses", 149.90, "https://http2.mlstatic.com/D_NQ_NP_780876-MLA50510738906_062022-O.webp", "perfum", false)
        )
    )

    val products: StateFlow<List<Product>> = _products

    private val _cart = MutableStateFlow<List<Product>>(emptyList())
    val cart: StateFlow<List<Product>> = _cart


    fun addToCart(product: Product) {
        _cart.value = _cart.value + product

        val item = Bundle().apply {
            putString("item_id", product.id.toString())
            putString("item_name", product.name)
        }

        val items = arrayListOf(item)

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, product.price)
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle)
    }

    fun removeFromCart(product: Product) {
        _cart.value = _cart.value.toMutableList().also { it.remove(product) }

        val item = Bundle().apply {
            putString("item_id", product.id.toString())
            putString("item_name", product.name)
        }

        val items = arrayListOf(item)
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, product.price)
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, bundle)
    }

    fun clickWishlistButton(product: Product): Boolean {
        val item = Bundle().apply {
            putString("item_id", product.id.toString())
            putString("item_name", product.name)
        }

        val items = arrayListOf(item)
        val bundle = Bundle().apply{
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, product.price)
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items)
        }
        if (product.isOnWishList == false) {
            product.isOnWishList = true
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, bundle)
            return true
        }
        else {
            product.isOnWishList = false;
            firebaseAnalytics.logEvent("remove_from_wishlist", bundle)
            return false;
        }
    }

    fun logBeginCheckout(products: List<Product>) {
        val items = products.map { product ->
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
                putInt(FirebaseAnalytics.Param.QUANTITY, 1)
            }
        }

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, products.sumOf { it.price })
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle)
    }

    fun logProductSelected(product: Product){
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.CONTENT_TYPE, product.type)
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun logAddPaymentInfo(products : List<Product>, option : String){
        val items = products.map { product ->
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
                putInt(FirebaseAnalytics.Param.QUANTITY, 1)
            }
        }

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putString("payment_method", option)
            putDouble(FirebaseAnalytics.Param.VALUE, products.sumOf { it.price })
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, bundle)
    }

    fun logViewCart(products : List<Product>){
        val items = products.map {
            product ->
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
                putInt(FirebaseAnalytics.Param.QUANTITY, 1)
            }
        }
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, products.sumOf { it.price })
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, bundle)
    }

    fun logViewItemList(products : List<Product>){
        val items = products.map {
            product ->
                Bundle().apply {
                    putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                    putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                }
        }

        val bundle = Bundle().apply{
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle)
    }

    fun logGenerateLead(products : List<Product>){
        val items = products.map { product ->
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
                putInt(FirebaseAnalytics.Param.QUANTITY, 1)
            }
        }

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, products.sumOf { it.price })
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.GENERATE_LEAD, bundle)
    }

    fun logAddShippingInfo(products: List<Product>, info : String){
        val items = products.map { product ->
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, product.id.toString())
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
                putInt(FirebaseAnalytics.Param.QUANTITY, 1)
            }
        }

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putString("shipping_info", info)
            putDouble(FirebaseAnalytics.Param.VALUE, products.sumOf { it.price })
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, ArrayList(items))
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, bundle)

    }

    var userName by mutableStateOf("")
    var currency by mutableStateOf("")
    var selectedCountry by mutableStateOf<Country?>(null)

    fun setUserInfo(name: String, country: com.example.taggingeventsapp.Country) {
        userName = name
        selectedCountry = country
    }

    fun loginEvent() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, null)
    }

    fun setRealCurrency() {
        currencyAnalyticsHandler.realCurrencyHandler()
        currency = "real"
    }

    fun setDollarCurrency() {
        currencyAnalyticsHandler.dollarCurrencyHandler()
        currency = "dollar"
    }

    fun setUserNameAnalytics(name: String) {
        firebaseAnalytics.setUserProperty("username", name)
    }
    fun setCanadianDollarCurrency() {
        currencyAnalyticsHandler.canadianDollarCurrencyHandler()
        currency = "canadian dollar"
    }

    fun setEuroCurrency() {
        currencyAnalyticsHandler.euroCurrencyHandler()
        currency = "euro"
    }

    fun setYenCurrency() {
        currencyAnalyticsHandler.yenCurrencyHandler()
        currency = "yen"
    }

    fun setMexicanPesoCurrency() {
        currencyAnalyticsHandler.mexicanPesoCurrencyHandler()
        currency = "mexican peso"
    }

    fun setArgentinePesoCurrency() {
        currencyAnalyticsHandler.argentinePesoCurrencyHandler()
        currency = "argentine peso"
    }

    fun setRupeeCurrency() {
        currencyAnalyticsHandler.rupeeCurrencyHandler()
        currency = "rupee"
    }

    fun setBrazilianNationality() {
        nationalityAnalyticsHandler.brazilianNationalityHandler()
    }

    fun setAmericanNationality() {
        nationalityAnalyticsHandler.americanNationalityHandler()
    }

    fun setFrenchNationality() {
        nationalityAnalyticsHandler.frenchNationalityHandler()
    }

    fun setGermanNationality() {
        nationalityAnalyticsHandler.germanNationalityHandler()
    }

    fun setJapaneseNationality() {
        nationalityAnalyticsHandler.japaneseNationalityHandler()
    }

    fun setItalianNationality() {
        nationalityAnalyticsHandler.italianNationalityHandler()
    }

    fun setCanadianNationality() {
        nationalityAnalyticsHandler.canadianNationalityHandler()
    }

    fun setMexicanNationality() {
        nationalityAnalyticsHandler.mexicanNationalityHandler()
    }

    fun setArgentineNationality() {
        nationalityAnalyticsHandler.argentineNationalityHandler()
    }

    fun setIndianNationality() {
        nationalityAnalyticsHandler.argentineNationalityHandler()
    }

}
