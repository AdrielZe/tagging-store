package com.example.taggingeventsapp

import com.google.firebase.analytics.FirebaseAnalytics

class NationalityAnalyticsHandler(private val firebaseAnalytics: FirebaseAnalytics) {

    fun brazilianNationalityHandler() {
        firebaseAnalytics.setUserProperty("nationality", "brazilian")
    }

    fun americanNationalityHandler() {
        firebaseAnalytics.setUserProperty("nationality", "american")
    }

    fun frenchNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "french")
    }

    fun germanNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "german")
    }

    fun japaneseNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "japanese")
    }

    fun italianNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "italian")
    }

    fun canadianNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "canadian")
    }

    fun mexicanNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "mexican")
    }

    fun argentineNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "argentine")
    }

    fun indianNationalityHandler(){
        firebaseAnalytics.setUserProperty("nationality", "indian")
    }
}
