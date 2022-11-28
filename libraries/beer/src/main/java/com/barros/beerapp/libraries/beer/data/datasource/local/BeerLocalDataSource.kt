package com.barros.beerapp.libraries.beer.data.datasource.local

import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel

internal interface BeerLocalDataSource {
    suspend fun getBeers(beerName: String? = null, page: Int, perPage: Int): List<BeerDatabaseModel>
    suspend fun getBeerById(beerId: Int): BeerDatabaseModel
    suspend fun insertBeers(beers: List<BeerDatabaseModel>)
}
