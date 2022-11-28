@file:OptIn(ExperimentalPagingApi::class)

package com.barros.beerapp.libraries.beer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.barros.beerapp.libraries.beer.data.database.mapper.mapFromDomainModel
import com.barros.beerapp.libraries.beer.data.database.mapper.mapToDomainModel
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSource
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSource
import com.barros.beerapp.libraries.beer.data.network.mapper.mapToDomainModel
import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.model.getResult
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
internal class BeerRepositoryImpl @Inject constructor(
    private val beerLocalDataSource: BeerLocalDataSource,
    private val beerRemoteDataSource: BeerRemoteDataSource
) : BeerRepository {

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val MAX_ITEM_PER_PAGE = 25
    }

    override suspend fun getBeerById(beerId: Int): Result<Beer> =
        getResult { beerLocalDataSource.getBeerById(beerId = beerId).mapToDomainModel() }

    override suspend fun getBeersPaging(beerName: String?): Flow<PagingData<Beer>> = Pager(
        config = PagingConfig(pageSize = MAX_ITEM_PER_PAGE),
        remoteMediator = BeerRemoteMediator(
            startingPageIndex = STARTING_PAGE_INDEX,
            getKeyById = { beerId ->
                beerLocalDataSource.getKeyById(beerId)
            },
            getRemoteData = { page ->
                beerRemoteDataSource.getBeers(
                    page = page,
                    beerName = beerName,
                    perPage = MAX_ITEM_PER_PAGE
                )
            },
            clearLocalData = {
                beerLocalDataSource.clearKeys()
                beerLocalDataSource.clearBeers()
            },
            saveLocalData = { keys, beers ->
                beerLocalDataSource.insertKeys(keys)
                beerLocalDataSource.insertBeers(beers)
            }
        )
    ) {
        beerLocalDataSource.getBeersPaging(beerName = beerName)
    }
        .flow
        .map { pagingData -> pagingData.map { beerDataBaseModel -> beerDataBaseModel.mapToDomainModel() } }

    override suspend fun getBeers(beerName: String?, page: Int): Flow<Result<List<Beer>>> =
        singleSourceOfTruthStrategy(
            readLocalData = {
                beerLocalDataSource.getBeers(beerName = beerName, page = page, perPage = MAX_ITEM_PER_PAGE).map(BeerDatabaseModel::mapToDomainModel)
            },
            readRemoteData = {
                beerRemoteDataSource.getBeers(beerName = beerName, page = page, perPage = MAX_ITEM_PER_PAGE).map(BeerNetworkModel::mapToDomainModel)
            },
            saveLocalData = { beers ->
                beerLocalDataSource.insertBeers(beers.map(Beer::mapFromDomainModel))
            }
        )

}
