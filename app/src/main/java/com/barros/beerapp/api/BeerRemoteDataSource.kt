package com.barros.beerapp.api

import javax.inject.Inject

class BeerRemoteDataSource @Inject constructor(
    private val beerApi: BeerApi
) : BeerDataSource() {

    suspend fun getBeers(page: Int, elements: Int) = getResult {
        beerApi.getBeers(page, elements)
    }

    suspend fun searchBeers(search: String, page: Int, elements: Int) = getResult {
        beerApi.searchBeer(search, page, elements)
    }
}
