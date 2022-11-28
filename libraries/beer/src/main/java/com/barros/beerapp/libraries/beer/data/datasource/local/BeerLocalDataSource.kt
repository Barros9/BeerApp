package com.barros.beerapp.libraries.beer.data.datasource.local

import androidx.paging.PagingSource
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.database.model.KeyDatabaseModel

internal interface BeerLocalDataSource {
    fun getBeersPaging(beerName: String? = null): PagingSource<Int, BeerDatabaseModel>
    suspend fun getBeers(beerName: String? = null, page: Int, perPage: Int): List<BeerDatabaseModel>
    suspend fun getBeerById(beerId: Int): BeerDatabaseModel
    suspend fun insertBeers(beers: List<BeerDatabaseModel>)
    suspend fun clearBeers()
    suspend fun insertKeys(keys: List<KeyDatabaseModel>)
    suspend fun getKeyById(beerId: Int): KeyDatabaseModel?
    suspend fun clearKeys()
}
