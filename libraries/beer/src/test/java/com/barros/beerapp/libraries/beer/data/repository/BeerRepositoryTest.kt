package com.barros.beerapp.libraries.beer.data.repository

import app.cash.turbine.test
import com.barros.beerapp.libraries.beer.data.database.mapper.mapToDomainModel
import com.barros.beerapp.libraries.beer.data.datasource.local.BeerLocalDataSource
import com.barros.beerapp.libraries.beer.data.datasource.remote.BeerRemoteDataSource
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.repository.BeerRepository
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
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
internal class BeerRepositoryTest {

    @MockK
    private lateinit var beerRemoteDataSource: BeerRemoteDataSource

    @MockK
    private lateinit var beerLocalDataSource: BeerLocalDataSource

    private lateinit var beerRepository: BeerRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        beerRepository = BeerRepositoryImpl(
            beerRemoteDataSource = beerRemoteDataSource,
            beerLocalDataSource = beerLocalDataSource
        )
    }

    @Test
    fun `call getBeers and return a list of beer saved locally`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeers(any(), any(), any()) } returns BeerMock.listOfBeerDatabaseModel

        // When
        beerRepository.getBeers(search = "", page = 1).test {
            val localData = awaitItem()
            awaitComplete()

            // Then
            coVerify { beerLocalDataSource.getBeers(any(), any(), any()) }
            assertEquals(BeerMock.listOfBeerDatabaseModel.map { it.mapToDomainModel() }, (localData as Result.Success<List<Beer>>).data)
        }
    }

    @Test
    fun `call getBeers and return an error reading from database`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeers(any(), any(), any()) } throws Exception()

        // When
        beerRepository.getBeers(
            search = "" +
                "",
            page = 1
        ).test {
            val localData = awaitItem()
            awaitComplete()

            // Then
            coVerify(exactly = 1) { beerLocalDataSource.getBeers(any(), any(), any()) }
            assertTrue { localData is Result.Error }
        }
    }

    @Test
    fun `call getBeers and return a list of beer fetched from remote cause local list is empty`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeers(any(), any(), any()) } returnsMany listOf(emptyList(), BeerMock.listOfBeerDatabaseModel)
        coEvery { beerRemoteDataSource.getBeers(any(), any(), any()) } returns BeerMock.listOfBeerNetworkModel
        coEvery { beerLocalDataSource.insertBeers(any()) } returns Unit

        // When
        beerRepository.getBeers(search = "", page = 1).test {
            val localData = awaitItem()
            val remoteData = awaitItem()
            awaitComplete()

            // Then
            coVerify(exactly = 2) { beerLocalDataSource.getBeers(any(), any(), any()) }
            coVerify(exactly = 1) { beerRemoteDataSource.getBeers(any(), any(), any()) }
            coVerify(exactly = 1) { beerLocalDataSource.insertBeers(any()) }
            assertTrue { (localData as Result.Success<List<Beer>>).data.isEmpty() }
            assertEquals(BeerMock.listOfBeerDatabaseModel.map { it.mapToDomainModel() }, (remoteData as Result.Success<List<Beer>>).data)
        }
    }

    @Test
    fun `call getBeers and return an error cause local list is empty, remote data is working but there is an error reading from database`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeers(any(), any(), any()) } returns emptyList() andThenThrows Exception("Error")
        coEvery { beerRemoteDataSource.getBeers(any(), any(), any()) } returns BeerMock.listOfBeerNetworkModel

        // When
        beerRepository.getBeers(search = "", page = 1).test {
            val localData = awaitItem()
            val remoteData = awaitItem()
            awaitComplete()

            // Then
            coVerify(exactly = 2) { beerLocalDataSource.getBeers(any(), any(), any()) }
            coVerify(exactly = 1) { beerRemoteDataSource.getBeers(any(), any(), any()) }
            coVerify(exactly = 1) { beerLocalDataSource.insertBeers(any()) }
            assertTrue { (localData as Result.Success<List<Beer>>).data.isEmpty() }
            assertTrue { remoteData is Result.Error }
        }
    }

    @Test
    fun `call getBeers and return an empty list cause local list is empty and remote data throws exception`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeers(any(), any(), any()) } returns emptyList()
        coEvery { beerRemoteDataSource.getBeers(any(), any(), any()) } throws Exception("Error")

        // When
        beerRepository.getBeers(search = "", page = 1).test {
            val localData = awaitItem()
            awaitComplete()

            // Then
            coVerify(exactly = 1) { beerLocalDataSource.getBeers(any(), any(), any()) }
            coVerify(exactly = 1) { beerRemoteDataSource.getBeers(any(), any(), any()) }
            assertTrue { (localData as Result.Success<List<Beer>>).data.isEmpty() }
        }
    }

    @Test
    fun `call getBeerById and return a Beer object`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeerById(any()) } returns BeerMock.listOfBeerDatabaseModel[2]

        // When
        val result = beerRepository.getBeerById(beerId = 2)

        // Then
        coVerify { beerLocalDataSource.getBeerById(any()) }
        assertEquals(BeerMock.listOfBeerDatabaseModel[2].mapToDomainModel(), (result as Result.Success<Beer>).data)
    }

    @Test
    fun `call getBeerById and return an error`() = runTest {
        // Given
        coEvery { beerLocalDataSource.getBeerById(any()) } throws Exception("Error")

        // When
        val result = beerRepository.getBeerById(beerId = 2)

        // Then
        coVerify { beerLocalDataSource.getBeerById(any()) }
        assert(result is Result.Error)
    }
}
