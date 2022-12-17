package com.barros.beerapp.libraries.beer.domain.repository

import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(beerName: String? = null, page: Int): Flow<Result<List<Beer>>>
    suspend fun getBeerById(beerId: Int): Result<Beer>
}
