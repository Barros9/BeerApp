package com.barros.beerapp.api

import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.util.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class BeerRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : BeerDataSource() {

    suspend fun getBeers(page: Int) = getResult {
        httpClient.get<List<BeerItem>>("${BASE_URL}beers?page=$page")
    }

    suspend fun searchBeers(search: String, page: Int) = getResult {
        httpClient.get<List<BeerItem>>("${BASE_URL}beers?beer_name=$search&page=$page")
    }
}
