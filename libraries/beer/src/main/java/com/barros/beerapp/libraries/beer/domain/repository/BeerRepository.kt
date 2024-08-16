package com.barros.beerapp.libraries.beer.domain.repository

import com.barros.beerapp.libraries.beer.domain.model.BeerModel
import com.barros.beerapp.libraries.beer.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(search: String, page: Int): Flow<Result<List<BeerModel>>>
    suspend fun getBeerById(beerId: Int): Result<BeerModel>
}
