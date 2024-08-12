package com.barros.beerapp.features.home.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.barros.beerapp.features.home.R
import com.barros.beerapp.libraries.beer.domain.BeerFake
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
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
        coEvery { homeViewModel.isLoadingNextPage } returns mutableStateOf(false)
        coEvery { homeViewModel.isPaginationExhaust } returns mutableStateOf(false)
        coEvery { homeViewModel.search } returns MutableStateFlow("")

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(
                    onSelectBeer = {},
                    homeViewModel = homeViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).assertIsDisplayed()
    }

    @Test
    fun beerListIsDisplayed() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Success(BeerFake.listOfBeers))
        coEvery { homeViewModel.isLoadingNextPage } returns mutableStateOf(false)
        coEvery { homeViewModel.isPaginationExhaust } returns mutableStateOf(false)
        coEvery { homeViewModel.search } returns MutableStateFlow("")

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(
                    onSelectBeer = {},
                    homeViewModel = homeViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(BeerFake.listOfBeers[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(BeerFake.listOfBeers[1].name).assertIsDisplayed()
    }

    @Test
    fun onClickSelectBeer() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Success(BeerFake.listOfBeers))
        coEvery { homeViewModel.isLoadingNextPage } returns mutableStateOf(false)
        coEvery { homeViewModel.isPaginationExhaust } returns mutableStateOf(false)
        coEvery { homeViewModel.search } returns MutableStateFlow("")

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(
                    onSelectBeer = {},
                    homeViewModel = homeViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(BeerFake.listOfBeers[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(BeerFake.listOfBeers[0].name).performClick()
    }

    @Test
    fun onClickRetry() {
        // Given
        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Error)
        coEvery { homeViewModel.isLoadingNextPage } returns mutableStateOf(false)
        coEvery { homeViewModel.isPaginationExhaust } returns mutableStateOf(false)
        coEvery { homeViewModel.search } returns MutableStateFlow("")

        // When
        composeTestRule.setContent {
            BeerAppTheme {
                HomeScreen(
                    onSelectBeer = {},
                    homeViewModel = homeViewModel
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_retry)).performClick()

        coVerify { homeViewModel.onRetry() }
    }

    // TODO fix test
//    @Test
//    fun beerListSearch() {
//        // Given
//        coEvery { homeViewModel.uiState } returns mutableStateOf(HomeUiState.Success(BeerFake.listOfBeers))
//        coEvery { homeViewModel.isLoadingNextPage } returns mutableStateOf(false)
//        coEvery { homeViewModel.isPaginationExhaust } returns mutableStateOf(false)
//        coEvery { homeViewModel.search } returnsMany listOf(MutableStateFlow(""), MutableStateFlow(BeerFake.listOfBeers[0].name))
//
//        // When
//        composeTestRule.setContent {
//            BeerAppTheme {
//                HomeScreen(
//                    onSelectBeer = {},
//                    homeViewModel = homeViewModel
//                )
//            }
//        }
//
//        // Then
//        composeTestRule.onNodeWithText(BeerFake.listOfBeers[0].name).assertIsDisplayed()
//        composeTestRule.onNodeWithText(BeerFake.listOfBeers[1].name).assertIsDisplayed()
//
//        composeTestRule.onNodeWithTag("search").performClick()
//        composeTestRule.onNodeWithTag("search").performTextReplacement(BeerFake.listOfBeers[0].name)
//        composeTestRule.onNodeWithTag("search").performImeAction()
//        composeTestRule.onNodeWithTag("search").assert(hasText(BeerFake.listOfBeers[0].name))
//        composeTestRule.onAllNodes(hasText(BeerFake.listOfBeers[0].name)).assertCountEquals(1)
//        composeTestRule.onAllNodes(hasText(BeerFake.listOfBeers[1].name)).assertCountEquals(0)
//    }
}
