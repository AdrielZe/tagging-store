package com.example.taggingeventsapp.analytics;

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taggingeventsapp.MainViewModel
import com.google.firebase.analytics.FirebaseAnalytics

class CurrencyAnalyticsHandler (private val firebaseAnalytics: FirebaseAnalytics) {

    // Brazil currency
    fun realCurrencyHandler() {
        firebaseAnalytics.setUserProperty("currency", "real")
    }

    // USA currency
    fun dollarCurrencyHandler() {
        firebaseAnalytics.setUserProperty("currency", "dollar")
    }

    // Canada currency
    fun canadianDollarCurrencyHandler(){
        firebaseAnalytics.setUserProperty("currency", "canadian dollar")
    }

    // France currency
    // Germany currency
    // Italy currency
    fun euroCurrencyHandler() {
        firebaseAnalytics.setUserProperty("currency", "euro")
    }

    // Japan currency
    fun yenCurrencyHandler() {
        firebaseAnalytics.setUserProperty("currency", "yen")
    }

    // Mexico currency
    fun mexicanPesoCurrencyHandler(){
        firebaseAnalytics.setUserProperty("currency", "mexican peso")
    }

    // Argentina currency
    fun argentinePesoCurrencyHandler(){
        firebaseAnalytics.setUserProperty("currency", "argentine peso")
    }

    // India currency
    fun rupeeCurrencyHandler(){
        firebaseAnalytics.setUserProperty("currency", "rupee")
    }

}
