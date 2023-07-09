package com.barros.beerapp.libraries.beer.data.repository

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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BeerRepositoryImpl @Inject constructor(
    private val beerLocalDataSource: BeerLocalDataSource,
    private val beerRemoteDataSource: BeerRemoteDataSource
) : BeerRepository {

    companion object {
        const val MAX_ITEM_PER_PAGE = 25
        // TODO max page is 13, puoi bloccare tutto qui
    }

    override suspend fun getBeerById(beerId: Int): Result<Beer> =
        getResult { beerLocalDataSource.getBeerById(beerId = beerId).mapToDomainModel() }

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
