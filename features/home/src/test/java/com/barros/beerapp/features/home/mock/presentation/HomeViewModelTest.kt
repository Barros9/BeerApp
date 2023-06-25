package com.barros.beerapp.features.home.mock.presentation

import com.barros.beerapp.features.home.mock.HomeMock
import com.barros.beerapp.features.home.presentation.HomeViewModel
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.navigator.navigation.Navigator
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
    private lateinit var navigator: Navigator

    @MockK
    private lateinit var getBeersUseCase: GetBeersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        homeViewModel = HomeViewModel(navigator = navigator, getBeersUseCase = getBeersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `called getBeersUseCase and returns successfully a list of beers`() = runTest {
        // Given
        assertEquals(HomeUiState.Loading, homeViewModel.uiState.value)
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(HomeMock.listOfBeers))

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
        assertEquals(HomeUiState.ShowBeers(HomeMock.listOfBeers, false), homeViewModel.uiState.value)
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
        coEvery { getBeersUseCase(any(), any()) } returns flowOf(Result.Success(HomeMock.listOfBeers))

        // When
        dispatcher.scheduler.advanceUntilIdle()
        homeViewModel.onRetry()

        // Then
        coVerify { getBeersUseCase(any(), any()) }
    }
}
