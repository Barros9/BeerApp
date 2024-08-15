package com.barros.beerapp.libraries.beer.domain.usecase

import com.barros.beerapp.libraries.beer.domain.model.BeerModel
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeerByIdUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {
    suspend operator fun invoke(beerId: Int): Result<BeerModel> =
        beerRepository.getBeerById(beerId = beerId)
}
