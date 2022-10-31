package com.barros.beerapp.features.detail.presentation.mock

import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal object DetailMock {

    val beer = Beer(
        id = 1,
        name = "Buzz",
        tagline = "A Real Bitter Experience.",
        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
        imageUrl = "https://images.punkapi.com/v2/keg.png"
    )
}
