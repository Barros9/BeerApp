package com.barros.beerapp.libraries.beer.data.network

import com.barros.beerapp.libraries.beer.data.network.model.BeerNetworkModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BeerApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beerName: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerNetworkModel>
}
