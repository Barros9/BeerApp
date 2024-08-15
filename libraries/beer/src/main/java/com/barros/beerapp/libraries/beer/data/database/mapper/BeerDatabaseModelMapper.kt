package com.barros.beerapp.libraries.beer.data.database.mapper

import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.domain.model.BeerModel

internal fun BeerDatabaseModel.mapToDomainModel() =
    BeerModel(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )

internal fun BeerModel.mapFromDomainModel() =
    BeerDatabaseModel(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )
