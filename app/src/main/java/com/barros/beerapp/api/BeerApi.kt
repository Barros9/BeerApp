package com.barros.beerapp.api

import com.barros.beerapp.model.BeerItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = 25
    ): Response<List<BeerItem>>

    @GET("beers")
    suspend fun searchBeer(
        @Query("beer_name") beerName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = 25
    ): Response<List<BeerItem>>
}
