package com.barros.beerapp.repository

import com.barros.beerapp.api.BeerApi
import com.barros.beerapp.api.BeerDataSource
import com.barros.beerapp.database.BeerDao
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Result
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@Singleton
class BeerRepository @Inject constructor(
    private val beerApi: BeerApi,
    private val beerDao: BeerDao
) : BeerDataSource() {

    @InternalCoroutinesApi
    suspend fun getBeers(search: String, page: Int): Flow<Result<List<BeerItem>>> {
        return if (search.isEmpty()) {
            loadBeers(
                loadFromDb = { beerDao.getBeers() },
                createCall = { getResult { beerApi.getBeers(page) } },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        } else {
            loadBeers(
                loadFromDb = { beerDao.getBeersFilteredByName("%$search%") },
                createCall = { getResult { beerApi.searchBeer(search, page) } },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        }
    }
}
