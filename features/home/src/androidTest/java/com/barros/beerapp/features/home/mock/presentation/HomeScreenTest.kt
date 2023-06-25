package com.barros.beerapp.features.home.mock.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.barros.beerapp.features.home.R
import com.barros.beerapp.features.home.mock.HomeMock
import com.barros.beerapp.features.home.presentation.HomeScreen
import com.barros.beerapp.features.home.presentation.HomeViewModel
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @MockK
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun errorIsDisplayed() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Error)

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(homeViewModel = homeViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).assertIsDisplayed()
    }

    @Test
    fun beerListIsDisplayed() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.ShowBeers(HomeMock.listOfBeers, false))

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(homeViewModel = homeViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText(HomeMock.listOfBeers[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(HomeMock.listOfBeers[1].name).assertIsDisplayed()
    }

    @Test
    fun onClickSelectBeer() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.ShowBeers(HomeMock.listOfBeers, false))
        coEvery { homeViewModel.onSelectBeer(any()) } returns Unit

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(homeViewModel = homeViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText(HomeMock.listOfBeers[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(HomeMock.listOfBeers[0].name).performClick()

        coVerify { homeViewModel.onSelectBeer(any()) }
    }

    @Test
    fun onClickRetry() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Error)

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(homeViewModel = homeViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).performClick()

        coVerify { homeViewModel.onRetry() }
    }
}
