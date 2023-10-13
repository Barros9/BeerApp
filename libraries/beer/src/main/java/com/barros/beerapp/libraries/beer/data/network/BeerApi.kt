package com.barros.beerapp.libraries.beer.data.network

import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BeerApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beerName: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 25
    ): List<BeerNetworkModel>
}
