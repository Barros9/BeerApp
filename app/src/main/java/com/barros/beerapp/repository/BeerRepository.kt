package com.barros.beerapp.repository

import com.barros.beerapp.api.BeerRemoteDataSource
import com.barros.beerapp.database.BeerDao
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Result
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class BeerRepository @Inject constructor(
    private val remoteSource: BeerRemoteDataSource,
    private val beerDao: BeerDao
) {

    fun getBeers(search: String, page: Int): Flow<Result<List<BeerItem>>> {
        return if (search.isEmpty()) {
            loadBeers(
                loadFromDb = { beerDao.getBeers() },
                createCall = { remoteSource.getBeers(page) },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        } else {
            loadBeers(
                loadFromDb = { beerDao.getBeersFilteredByName("%$search%") },
                createCall = { remoteSource.searchBeers(search, page) },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        }
    }
}
