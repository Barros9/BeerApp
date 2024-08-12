package com.barros.beerapp.libraries.beer.domain.usecase

import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetBeerByIdUseCaseTest {

    @MockK
    private lateinit var beerRepository: BeerRepository

    private lateinit var getBeerByIdUseCase: GetBeerByIdUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getBeerByIdUseCase = GetBeerByIdUseCase(beerRepository = beerRepository)
    }

    @Test
    fun `use case should call repository and return a beer`() = runTest {
        // Given
        coEvery { beerRepository.getBeerById(any()) } returns Result.Success(BeerFake.listOfBeers[1])

        // When
        val result = getBeerByIdUseCase(beerId = 1)

        // Then
        coVerify { beerRepository.getBeerById(any()) }
        assertEquals(BeerFake.listOfBeers[1], (result as Result.Success<Beer>).data)
    }
}
