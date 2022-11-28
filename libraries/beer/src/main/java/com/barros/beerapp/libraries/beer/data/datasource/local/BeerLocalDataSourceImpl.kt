package com.barros.beerapp.libraries.beer.data.datasource.local

import androidx.paging.PagingSource
import com.barros.beerapp.libraries.beer.data.database.BeerDao
import com.barros.beerapp.libraries.beer.data.database.KeyDao
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.database.model.KeyDatabaseModel
import javax.inject.Inject

internal class BeerLocalDataSourceImpl @Inject constructor(
    private val beerDao: BeerDao,
    private val keyDao: KeyDao
) : BeerLocalDataSource {
    override fun getBeersPaging(beerName: String?): PagingSource<Int, BeerDatabaseModel> =
        beerDao.getBeersPaging(beerName = beerName)

    override suspend fun getBeers(beerName: String?, page: Int, perPage: Int): List<BeerDatabaseModel> =
        beerDao.getBeers(beerName = beerName, offset = (page - 1) * perPage, limitPerPage = perPage)

    override suspend fun getBeerById(beerId: Int): BeerDatabaseModel =
        beerDao.getBeerById(beerId = beerId)

    override suspend fun insertBeers(beers: List<BeerDatabaseModel>) =
        beerDao.insertBeers(beers = beers)

    override suspend fun clearBeers() =
        beerDao.clear()

    override suspend fun insertKeys(keys: List<KeyDatabaseModel>) =
        keyDao.insertKeys(keys = keys)

    override suspend fun getKeyById(beerId: Int): KeyDatabaseModel? =
        keyDao.getKeyById(beerId = beerId)

    override suspend fun clearKeys() =
        keyDao.clear()
}
