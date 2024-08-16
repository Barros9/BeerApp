package com.barros.beerapp.features.detail.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.barros.beerapp.features.detail.R
import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @MockK
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun errorIsDisplayed() {
        // Given
        coEvery { detailViewModel.uiState } returns mutableStateOf(DetailUiState.Error)

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                DetailScreen(
                    onNavigateUp = {},
                    detailViewModel = detailViewModel
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText(context.resources.getString(R.string.detail_error))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(context.resources.getString(R.string.detail_retry))
            .assertIsDisplayed()
    }

    @Test
    fun beerIsDisplayed() {
        // Given
        coEvery { detailViewModel.uiState } returns mutableStateOf(DetailUiState.ShowBeer(BeerFake.buzzBeerModel))

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                DetailScreen(
                    onNavigateUp = {},
                    detailViewModel = detailViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(BeerFake.buzzBeerModel.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(BeerFake.buzzBeerModel.description).assertIsDisplayed()
    }

    @Test
    fun onNavigateUp() {
        // Given
        coEvery { detailViewModel.uiState } returns mutableStateOf(DetailUiState.ShowBeer(BeerFake.buzzBeerModel))

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                DetailScreen(
                    onNavigateUp = {},
                    detailViewModel = detailViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.detail_go_back))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.detail_go_back))
            .performClick()
    }
}
