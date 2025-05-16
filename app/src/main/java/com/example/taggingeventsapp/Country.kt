package com.example.taggingeventsapp

data class Country(
    val code: String,
    val name: String,
    val flagUrl: String
)

val countries = listOf(
    Country("BR", "Brasil", "https://flagcdn.com/w40/br.png"),
    Country("US", "Estados Unidos", "https://flagcdn.com/w40/us.png"),
    Country("FR", "França", "https://flagcdn.com/w40/fr.png"),
    Country("DE", "Alemanha", "https://flagcdn.com/w40/de.png"),
    Country("JP", "Japão", "https://flagcdn.com/w40/jp.png"),
    Country("IT", "Itália", "https://flagcdn.com/w40/it.png"),
    Country("CA", "Canadá", "https://flagcdn.com/w40/ca.png"),
    Country("MX", "México", "https://flagcdn.com/w40/mx.png"),
    Country("AR", "Argentina", "https://flagcdn.com/w40/ar.png"),
    Country("IN", "Índia", "https://flagcdn.com/w40/in.png")
)
