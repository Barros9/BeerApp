package com.barros.beerapp.libraries.beer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.barros.beerapp.libraries.beer.data.database.mapper.mapFromDomainModel
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.database.model.KeyDatabaseModel
import com.barros.beerapp.libraries.beer.data.network.mapper.mapToDomainModel
import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import com.barros.beerapp.libraries.beer.domain.entity.Beer

@OptIn(ExperimentalPagingApi::class)
internal class BeerRemoteMediator(
    val startingPageIndex: Int,
    val getRemoteData: suspend (Int) -> List<BeerNetworkModel>,
    val getKeyById: suspend (Int) -> KeyDatabaseModel?,
    val clearLocalData: suspend () -> Unit,
    val saveLocalData: suspend (List<KeyDatabaseModel>, List<BeerDatabaseModel>) -> Unit
) : RemoteMediator<Int, BeerDatabaseModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerDatabaseModel>
    ): MediatorResult =
        runCatching {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getKeyClosestToCurrentPosition(state = state)
                    remoteKeys?.nextKey?.minus(1) ?: startingPageIndex
                }
                LoadType.PREPEND -> {
                    val key = getKeyForFirstItem(state = state)
                    val prevKey = key?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = key != null
                    )
                    prevKey
                }
                LoadType.APPEND -> {
                    val key = getKeyForLastItem(state = state)
                    val nextKey = key?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = key != null
                    )
                    nextKey
                }
            }

            val remoteData = getRemoteData(page).map(BeerNetworkModel::mapToDomainModel)
            val endOfPaginationReached = remoteData.isEmpty()

            if (loadType == LoadType.REFRESH) {
                clearLocalData()
            }

            val prevKey = if (page == startingPageIndex) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = remoteData.map {
                KeyDatabaseModel(
                    beerId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            saveLocalData(keys, remoteData.map(Beer::mapFromDomainModel))

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }.getOrElse {
            MediatorResult.Error(it)
        }

    private suspend fun getKeyClosestToCurrentPosition(state: PagingState<Int, BeerDatabaseModel>): KeyDatabaseModel? =
        state
            .anchorPosition
            ?.let { position ->
                state.closestItemToPosition(position)
                    ?.id
                    ?.let { beerId -> getKeyById(beerId) }
            }


    private suspend fun getKeyForFirstItem(state: PagingState<Int, BeerDatabaseModel>): KeyDatabaseModel? =
        state
            .pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { beer -> getKeyById(beer.id) }

    private suspend fun getKeyForLastItem(state: PagingState<Int, BeerDatabaseModel>): KeyDatabaseModel? =
        state
            .pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { beer -> getKeyById(beer.id) }

}