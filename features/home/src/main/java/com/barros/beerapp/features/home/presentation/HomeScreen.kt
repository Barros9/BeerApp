package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.uiState
    val isLoadingNextPage by homeViewModel.isLoadingNextPage
    val isPaginationExhaust by homeViewModel.isPaginationExhaust

    HomeContent(
        modifier = Modifier,
        uiState = uiState,
        isLoadingNextPage = isLoadingNextPage,
        isPaginationExhaust = isPaginationExhaust,
        onSelectBeer = { beerId -> homeViewModel.onSelectBeer(beerId) },
        onRetry = { homeViewModel.onRetry() },
        onSearchNextPage = { homeViewModel.onSearchNextPage() }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    onSelectBeer: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchNextPage: () -> Unit
) {
    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            HomeHeader(
                modifier = modifier
            )
            HomeList(
                modifier = modifier,
                uiState = uiState,
                isLoadingNextPage = isLoadingNextPage,
                isPaginationExhaust = isPaginationExhaust,
                onSelectBeer = onSelectBeer,
                onRetry = onRetry,
                onSearchNextPage = onSearchNextPage
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreviewEmpty() {
    BeerAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState.Empty,
            isLoadingNextPage = false,
            isPaginationExhaust = false,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}
