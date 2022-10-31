package com.barros.beerapp.libraries.beer.data.database.mapper

import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal fun BeerDatabaseModel.mapToDomainModel() =
    Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )

internal fun Beer.mapFromDomainModel() =
    BeerDatabaseModel(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl
    )
