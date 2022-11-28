package com.barros.beerapp.libraries.beer.domain.usecase

import androidx.paging.PagingData
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBeersPagingUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(beerName: String? = null): Flow<PagingData<Beer>> =
        beerRepository.getBeersPaging(beerName = beerName)
}
