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
    override fun getBeers(beerName: String?): PagingSource<Int, BeerDatabaseModel> =
        beerDao.getBeers(beerName)

    override suspend fun getBeerById(beerId: Int): BeerDatabaseModel =
        beerDao.getBeerById(beerId)

    override suspend fun insertBeers(beers: List<BeerDatabaseModel>) =
        beerDao.insertBeers(beers)

    override suspend fun clearBeers() =
        beerDao.clear()

    override suspend fun insertKeys(keys: List<KeyDatabaseModel>) =
        keyDao.insertKeys(keys)

    override suspend fun getKeyById(beerId: Int): KeyDatabaseModel? =
        keyDao.getKeyById(beerId)

    override suspend fun clearKeys() =
        keyDao.clear()
}
