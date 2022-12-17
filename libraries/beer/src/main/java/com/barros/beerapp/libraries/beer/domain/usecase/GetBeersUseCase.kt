package com.barros.beerapp.libraries.beer.domain.usecase

import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(beerName: String? = null, page: Int): Flow<Result<List<Beer>>> =
        beerRepository.getBeers(beerName = beerName, page = page)
}
