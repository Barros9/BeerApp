package com.barros.beerapp.libraries.beer.data.datasource.remote

import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel

internal interface BeerRemoteDataSource {
    suspend fun getBeers(search: String, page: Int, perPage: Int): List<BeerNetworkModel>
}
