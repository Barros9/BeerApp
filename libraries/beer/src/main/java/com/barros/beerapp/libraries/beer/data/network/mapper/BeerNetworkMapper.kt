package com.barros.beerapp.libraries.beer.data.network.mapper

import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal fun BeerNetworkModel.mapToDomainModel() =
    Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )
