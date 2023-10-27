package com.barros.beerapp.libraries.beer.data.datasource

import com.barros.beerapp.libraries.beer.data.database.BeerDao
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSource
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSourceImpl
import com.barros.beerapp.libraries.beer.mock.BeerMock
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
internal class BeerLocalDataSourceTest {

    @MockK
    private lateinit var beerDao: BeerDao

    private lateinit var beerLocalDataSource: BeerLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        beerLocalDataSource = BeerLocalDataSourceImpl(beerDao = beerDao)
    }

    @Test
    fun `get beers with success`() = runTest {
        // Given
        coEvery { beerDao.getBeers(any(), any(), any()) } returns BeerMock.listOfBeerDatabaseModel

        // When
        val result = beerLocalDataSource.getBeers(search = "", page = 1, perPage = 5)

        // Then
        coVerify { beerDao.getBeers(any(), any(), any()) }
        assertEquals(BeerMock.listOfBeerDatabaseModel, result)
    }

    @Test
    fun `save beers with success`() = runTest {
        // Given
        coEvery { beerDao.insertBeers(any()) } returns Unit

        // When
        beerLocalDataSource.insertBeers(beers = BeerMock.listOfBeerDatabaseModel)

        // Then
        coVerify { beerDao.insertBeers(any()) }
    }

    @Test
    fun `get beer by id with success`() = runTest {
        // Given
        coEvery { beerDao.getBeerById(any()) } returns BeerMock.listOfBeerDatabaseModel[1]

        // When
        val result = beerLocalDataSource.getBeerById(beerId = 1)

        // Then
        coVerify { beerDao.getBeerById(any()) }
        assertEquals(BeerMock.listOfBeerDatabaseModel[1], result)
    }
}
