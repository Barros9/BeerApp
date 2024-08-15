package com.barros.beerapp.libraries.beer.domain.model

data class BeerModel(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String?
)
