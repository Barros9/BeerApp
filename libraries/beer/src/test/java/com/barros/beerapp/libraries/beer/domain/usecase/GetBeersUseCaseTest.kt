package com.barros.beerapp.libraries.beer.domain.usecase

import app.cash.turbine.test
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
import com.barros.beerapp.libraries.beer.mock.BeerMock
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
        coEvery { beerRepository.getBeers(any(), any()) } returns flowOf(Result.Success(BeerMock.listOfBeers))

        // When
        getBeersUseCase(page = 1).test {
            val result = awaitItem()
            awaitComplete()

            // Then
            coVerify { beerRepository.getBeers(any(), any()) }
            assertEquals(BeerMock.listOfBeers, (result as Result.Success<List<Beer>>).data)
        }
    }
}