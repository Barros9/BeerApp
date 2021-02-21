package com.barros.beerapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.barros.beerapp.api.BeerRemoteDataSource
import com.barros.beerapp.database.BeerDatabase
import com.barros.beerapp.model.BeerItem
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class BeerRepository @Inject constructor(
    private val remoteSource: BeerRemoteDataSource,
    private val database: BeerDatabase
) {

    @ExperimentalPagingApi
    fun getBeers(search: String): Flow<PagingData<BeerItem>> {

        val pagingSourceFactory = if (search.isEmpty()) {
            { database.beerDao().getBeers() }
        } else {
            { database.beerDao().getBeersFilteredByName("%$search%") }
        }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = BeerRemoteMediator(
                search,
                remoteSource,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}
