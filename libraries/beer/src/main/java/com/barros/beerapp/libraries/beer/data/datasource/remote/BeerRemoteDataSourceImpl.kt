package com.barros.beerapp.libraries.beer.data.datasource.remote

import com.barros.beerapp.libraries.beer.data.network.BeerApi
import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import javax.inject.Inject

internal class BeerRemoteDataSourceImpl @Inject constructor(
    private val beerApi: BeerApi
) : BeerRemoteDataSource {

    override suspend fun getBeers(
        search: String,
        page: Int,
        perPage: Int
    ): List<BeerNetworkModel> =
        beerApi.getBeers(beerName = search.ifBlank { null }, page = page, perPage = perPage)
}
