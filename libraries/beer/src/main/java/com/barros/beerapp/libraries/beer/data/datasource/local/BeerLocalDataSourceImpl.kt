package com.barros.beerapp.libraries.beer.data.datasource.local

import com.barros.beerapp.libraries.beer.data.database.BeerDao
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import javax.inject.Inject

internal class BeerLocalDataSourceImpl @Inject constructor(
    private val beerDao: BeerDao
) : BeerLocalDataSource {
    override suspend fun getBeers(search: String, page: Int, perPage: Int): List<BeerDatabaseModel> =
        beerDao.getBeers(search = search, offset = (page - 1) * perPage, limitPerPage = perPage)

    override suspend fun getBeerById(beerId: Int): BeerDatabaseModel =
        beerDao.getBeerById(beerId = beerId)

    override suspend fun insertBeers(beers: List<BeerDatabaseModel>) =
        beerDao.insertBeers(beers = beers)
}
