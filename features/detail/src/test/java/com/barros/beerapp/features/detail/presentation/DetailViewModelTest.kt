package com.barros.beerapp.features.detail.presentation

import androidx.lifecycle.SavedStateHandle
import com.barros.beerapp.features.detail.mock.DetailMock
import com.barros.beerapp.features.detail.presentation.model.DetailUiState
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeerByIdUseCase
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var detailViewModel: DetailViewModel

    @MockK
    private lateinit var navigator: Navigator

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getBeerByIdUseCase: GetBeerByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        detailViewModel = DetailViewModel(
            navigator = navigator,
            savedStateHandle = savedStateHandle,
            getBeerByIdUseCase = getBeerByIdUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `called getBeersUseCase and returns successfully a beer object`() = runTest {
        // Given
        assertEquals(DetailUiState.Loading, detailViewModel.uiState.value)
        coEvery { savedStateHandle.get<Int>(DetailDestination.BEER_ID_PARAM) } returns 1
        coEvery { getBeerByIdUseCase(any()) } returns Result.Success(DetailMock.beer)

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(DetailUiState.ShowBeer(DetailMock.beer), detailViewModel.uiState.value)
    }

    @Test
    fun `called getBeersUseCase and returns an error`() = runTest {
        // Given
        assertEquals(DetailUiState.Loading, detailViewModel.uiState.value)
        coEvery { savedStateHandle.get<Int>(DetailDestination.BEER_ID_PARAM) } returns 1
        coEvery { getBeerByIdUseCase(any()) } returns Result.Error(
            "ErrorMessage",
            Exception("Error")
        )

        // When
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(DetailUiState.Error, detailViewModel.uiState.value)
    }

    @Test
    fun `navigate up clicking on back button`() = runTest {
        // Given
        assertEquals(DetailUiState.Loading, detailViewModel.uiState.value)
        coEvery { savedStateHandle.get<Int>(DetailDestination.BEER_ID_PARAM) } returns 1
        coEvery { getBeerByIdUseCase(any()) } returns Result.Success(DetailMock.beer)
        coEvery { navigator.navigateUp() } returns true

        // When
        dispatcher.scheduler.advanceUntilIdle()
        detailViewModel.onNavigateUp()

        // Then
        verify { navigator.navigateUp() }
    }
}
