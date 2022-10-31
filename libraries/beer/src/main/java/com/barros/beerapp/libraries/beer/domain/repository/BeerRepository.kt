package com.barros.beerapp.libraries.beer.domain.repository

import androidx.paging.PagingData
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(beerName: String? = null): Flow<PagingData<Beer>>
    suspend fun getBeerById(beerId: Int): Result<Beer>
}
