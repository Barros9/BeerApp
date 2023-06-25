package com.barros.beerapp.libraries.beer.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BeerNetworkModel(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String? = ""
)
