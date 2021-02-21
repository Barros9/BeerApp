package com.barros.beerapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.barros.beerapp.api.BeerRemoteDataSource
import com.barros.beerapp.database.BeerDatabase
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Keys
import com.bumptech.glide.load.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val search: String,
    private val remoteSource: BeerRemoteDataSource,
    private val database: BeerDatabase
) : RemoteMediator<Int, BeerItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val keys = getKeyClosestToCurrentPosition(state)
                keys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val keys = getKeyForLastItem(state)
                if (keys?.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                keys.nextKey
            }
        }

        try {
            val beers = if (search.isEmpty()) {
                remoteSource.getBeers(page, state.config.pageSize)
            } else {
                remoteSource.searchBeers(search, page, state.config.pageSize)
            }

            val endOfPaginationReached = beers.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.keysDao().clearKeys()
                    database.beerDao().clearRepos()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = beers.map {
                    Keys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.keysDao().insertAll(keys)
                database.beerDao().insertAll(beers)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyForLastItem(state: PagingState<Int, BeerItem>): Keys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { beer ->
                database.keysDao().keysBeerId(beer.id)
            }
    }

    private suspend fun getKeyClosestToCurrentPosition(state: PagingState<Int, BeerItem>): Keys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { beerId ->
                database.keysDao().keysBeerId(beerId)
            }
        }
    }
}
