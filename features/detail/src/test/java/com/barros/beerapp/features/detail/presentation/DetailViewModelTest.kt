package com.barros.beerapp.features.detail.presentation

import androidx.lifecycle.SavedStateHandle
import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.beer.domain.model.Result.Error
import com.barros.beerapp.libraries.beer.domain.model.Result.Success
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeerByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
internal class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var detailViewModel: DetailViewModel

    @MockK
    private lateinit var getBeerByIdUseCase: GetBeerByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `called getBeersUseCase and returns successfully a beer object`() = runTest {
        // Given
        coEvery { getBeerByIdUseCase(any()) } returns Success(BeerFake.buzzBeerModel)

        val savedStateHandle = SavedStateHandle().apply {
            set(DetailNavigation::beerId.name, 1)
        }

        // When
        detailViewModel = DetailViewModel(
            savedStateHandle = savedStateHandle,
            getBeerByIdUseCase = getBeerByIdUseCase
        )

        // Then
        assertEquals(DetailUiState.Loading, detailViewModel.uiState.value)
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(DetailUiState.ShowBeer(BeerFake.buzzBeerModel), detailViewModel.uiState.value)
    }

    @Test
    fun `called getBeersUseCase and returns an error`() = runTest {
        // Given
        coEvery { getBeerByIdUseCase(any()) } returns Error(
            "ErrorMessage",
            Exception("Error")
        )

        val savedStateHandle = SavedStateHandle().apply {
            set(DetailNavigation::beerId.name, 1)
        }

        // When
        detailViewModel = DetailViewModel(
            savedStateHandle = savedStateHandle,
            getBeerByIdUseCase = getBeerByIdUseCase
        )

        // Then
        assertEquals(DetailUiState.Loading, detailViewModel.uiState.value)
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(DetailUiState.Error, detailViewModel.uiState.value)
    }
}
