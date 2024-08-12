package com.barros.beerapp.libraries.beer.data.datasource

import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSource
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSourceImpl
import com.barros.beerapp.libraries.beer.data.network.BeerApi
import com.barros.beerapp.libraries.beer.domain.BeerFake
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
internal class BeerRemoteDataSourceTest {

    @MockK
    private lateinit var beerApi: BeerApi

    private lateinit var beerRemoteDataSource: BeerRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        beerRemoteDataSource = BeerRemoteDataSourceImpl(beerApi = beerApi)
    }

    @Test
    fun `get beers with success`() = runTest {
        // Given
        coEvery { beerApi.getBeers(any(), any(), any()) } returns BeerFake.listOfBeerNetworkModel

        // When
        val response = beerRemoteDataSource.getBeers(search = "", page = 1, perPage = 5)

        // Then
        coVerify { beerApi.getBeers(any(), any(), any()) }
        assertEquals(BeerFake.listOfBeerNetworkModel, response)
    }
}
