package com.barros.beerapp.features.home.presentation

import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.domain.usecase.SaveThemePreferenceUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var homeViewModel: HomeViewModel

    @MockK
    private lateinit var getBeersUseCase: GetBeersUseCase

    @MockK
    private lateinit var saveThemePreferenceUseCase: SaveThemePreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        homeViewModel = HomeViewModel(
            getBeersUseCase = getBeersUseCase,
            saveThemePreferenceUseCase = saveThemePreferenceUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `called getBeersUseCase and returns successfully a list of beers`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(BeerFake.listOfBeers))

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
        assertEquals(HomeUiState.Success(BeerFake.listOfBeers), homeViewModel.uiState.value)
    }

    @Test
    fun `called getBeersUseCase and returns an empty list`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(emptyList()))

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
        assertEquals(HomeUiState.Empty, homeViewModel.uiState.value)
    }

    @Test
    fun `called getBeersUseCase and returns an error`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Error("Error", Exception("Error")))

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
        assertEquals(HomeUiState.Error, homeViewModel.uiState.value)
    }

    @Test
    fun `called getBeersUseCase and returns an error from exceptionHandler`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } throws Exception("Error")

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
        assertEquals(HomeUiState.Error, homeViewModel.uiState.value)
    }

    @Test
    fun `called onRetry and verify getBeersUseCase was called`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(BeerFake.listOfBeers))

        // When
        dispatcher.scheduler.advanceUntilIdle()
        homeViewModel.onRetry()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
    }

    @Test
    fun `called searchNextPage to show more items`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(BeerFake.listOfBeers))

        // When
        dispatcher.scheduler.advanceUntilIdle()
        homeViewModel.onSearchNextPage()
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getBeersUseCase(any(), any()) }
    }
}
