package com.barros.beerapp.libraries.beer.data.network.mapper

import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import com.barros.beerapp.libraries.beer.domain.model.BeerModel

internal fun BeerNetworkModel.mapToDomainModel() =
    BeerModel(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )
