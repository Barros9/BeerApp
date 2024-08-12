package com.barros.beerapp.libraries.beer.domain

import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import com.barros.beerapp.libraries.beer.domain.entity.Beer

object BeerFake {

    val buzzBeerModel = Beer(
        id = 1,
        name = "Buzz",
        tagline = "A Real Bitter Experience.",
        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
        imageUrl = "https://images.punkapi.com/v2/keg.png"
    )

    private val trashyBlondeBeerModel = Beer(
        id = 2,
        name = "Trashy Blonde",
        tagline = "You Know You Shouldn't",
        description = "A titillating, neurotic, peroxide punk of a Pale Ale. Combining attitude, style, substance, and a little bit of low self esteem for good measure; what would your mother say? The seductive lure of the sassy passion fruit hop proves too much to resist. All that is even before we get onto the fact that there are no additives, preservatives, pasteurization or strings attached. All wrapped up with the customary BrewDog bite and imaginative twist.",
        imageUrl = "https://images.punkapi.com/v2/2.png"
    )

    private val berlinerWeisseBeerModel = Beer(
        id = 3,
        name = "Berliner Weisse With Yuzu - B-Sides",
        tagline = "Japanese Citrus Berliner Weisse.",
        description = "Japanese citrus fruit intensifies the sour nature of this German classic.",
        imageUrl = "https://images.punkapi.com/v2/keg.png"
    )

    private val pilsenLagerBeerModel = Beer(
        id = 4,
        name = "Pilsen Lager",
        tagline = "Unleash the Yeast Series.",
        description = "Our Unleash the Yeast series was an epic experiment into the differences in aroma and flavour provided by switching up your yeast. We brewed up a wort with a light caramel note and some toasty biscuit flavour, and hopped it with Amarillo and Centennial for a citrusy bitterness. Everything else is down to the yeast. Pilsner yeast ferments with no fruity esters or spicy phenols, although it can add a hint of butterscotch.",
        imageUrl = "https://images.punkapi.com/v2/4.png"
    )

    private val averyBrownDredgeBeerModel = Beer(
        id = 5,
        name = "Avery Brown Dredge",
        tagline = "Bloggers' Imperial Pilsner.",
        description = "An Imperial Pilsner in collaboration with beer writers. Tradition. Homage. Revolution. We wanted to showcase the awesome backbone of the Czech brewing tradition, the noble Saaz hop, and also tip our hats to the modern beers that rock our world, and the people who make them.",
        imageUrl = "https://images.punkapi.com/v2/5.png"
    )

    val listOfBeers = listOf(
        buzzBeerModel,
        trashyBlondeBeerModel,
        berlinerWeisseBeerModel,
        pilsenLagerBeerModel,
        averyBrownDredgeBeerModel
    )

    internal val listOfBeerDatabaseModel = listOfBeers.map {
        BeerDatabaseModel(
            id = it.id,
            name = it.name,
            tagline = it.tagline,
            description = it.description,
            imageUrl = it.imageUrl
        )
    }

    internal val listOfBeerNetworkModel = listOfBeers.map {
        BeerNetworkModel(
            id = it.id,
            name = it.name,
            tagline = it.tagline,
            description = it.description,
            imageUrl = it.imageUrl
        )
    }
}
