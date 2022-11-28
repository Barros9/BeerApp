@file:OptIn(ExperimentalCoroutinesApi::class)

package com.barros.beerapp.libraries.beer.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.barros.beerapp.libraries.beer.mock.BeerMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class BeerDaoTest {
    private lateinit var beerAppDatabase: BeerAppDatabase

    @Before
    fun setup() {
        beerAppDatabase = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                BeerAppDatabase::class.java
            )
            .build()
    }

    @After
    fun tearDown() {
        beerAppDatabase.close()
    }

    @Test
    fun `insert and get a list of beer database model`() = runTest {
        // Given
        val listOfBeerDatabaseModel = BeerMock.listOfBeerDatabaseModel

        // When
        beerAppDatabase.beerDao().insertBeers(listOfBeerDatabaseModel)
        val resultFromDb = beerAppDatabase.beerDao().getBeersPaging(null)

        // Then
        assertEquals(resultFromDb, listOfBeerDatabaseModel)
    }

    @Test
    fun `insert and get a single beer database model`() = runTest {
        // Given
        val listOfBeerDatabaseModel = BeerMock.listOfBeerDatabaseModel

        // When
        beerAppDatabase.beerDao().insertBeers(listOfBeerDatabaseModel)
        val resultFromDb = beerAppDatabase.beerDao().getBeerById(3)

        // Then
        assertEquals(resultFromDb, listOfBeerDatabaseModel.first { it.id == 3 })
    }
}
