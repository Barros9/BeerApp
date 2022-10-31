package com.barros.beerapp.libraries.beer.domain.entity

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String?,
)
