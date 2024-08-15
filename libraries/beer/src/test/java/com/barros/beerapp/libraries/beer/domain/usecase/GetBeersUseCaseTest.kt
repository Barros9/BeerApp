package com.barros.beerapp.libraries.beer.domain.usecase

import app.cash.turbine.test
import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.beer.domain.model.BeerModel
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetBeersUseCaseTest {

    @MockK
    private lateinit var beerRepository: BeerRepository

    private lateinit var getBeersUseCase: GetBeersUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getBeersUseCase = GetBeersUseCase(beerRepository = beerRepository)
    }

    @Test
    fun `use case should call repository and return a list of beer`() = runTest {
        // Given
        coEvery { beerRepository.getBeers(any(), any()) } returns flowOf(Result.Success(BeerFake.listOfBeers))

        // When
        getBeersUseCase(search = "", page = 1).test {
            val result = awaitItem()
            awaitComplete()

            // Then
            coVerify { beerRepository.getBeers(any(), any()) }
            assertEquals(BeerFake.listOfBeers, (result as Result.Success<List<BeerModel>>).data)
        }
    }
}
