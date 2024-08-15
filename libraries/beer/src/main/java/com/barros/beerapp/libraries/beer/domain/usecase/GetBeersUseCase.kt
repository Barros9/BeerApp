package com.barros.beerapp.libraries.beer.domain.usecase

import com.barros.beerapp.libraries.beer.domain.model.BeerModel
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(search: String, page: Int): Flow<Result<List<BeerModel>>> =
        beerRepository.getBeers(search = search, page = page)
}
