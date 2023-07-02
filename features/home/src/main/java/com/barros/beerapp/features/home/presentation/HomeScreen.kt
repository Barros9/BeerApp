package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.barros.beerapp.features.home.R
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.barros.beerapp.libraries.ui.R as R_UI

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.uiState

    HomeContent(
        modifier = Modifier,
        uiState = uiState,
        onSelectBeer = { beerId -> homeViewModel.onSelectBeer(beerId) },
        onRetry = { homeViewModel.onRetry() },
        onSearchNextPage = { homeViewModel.searchNextPage() }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onSelectBeer: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchNextPage: () -> Unit
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.displayMedium,
                text = stringResource(R.string.home_title),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeUiState.Error -> {
                    Text(stringResource(R.string.home_retry_error))
                    Button(onClick = { onRetry() }) {
                        Text(stringResource(R.string.home_retry))
                    }
                }

                is HomeUiState.ShowBeers -> {
                    val listState = rememberLazyListState()

                    val isScrollToEnd by remember {
                        derivedStateOf {
                            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
                        }
                    }

                    if (isScrollToEnd && uiState.loadNextPage.not()) {
                        onSearchNextPage()
                    }

                    LazyColumn(state = listState) {
                        items(uiState.beers) { beer ->
                            BeerRow(
                                beer = beer,
                                onSelectBeer = { onSelectBeer(it) }
                            )
                        }
                        if (uiState.loadNextPage) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R_UI.dimen.spacing_16))
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }

                is HomeUiState.Empty -> {
                    Text(stringResource(R.string.home_empty))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreviewLoading() {
    BeerAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState.Empty,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreviewError() {
    BeerAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState.Error,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreviewShowBeers() {
    BeerAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState.ShowBeers(
                beers = listOf(
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    ),
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    ),
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    ),
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    ),
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    ),
                    Beer(
                        id = 0,
                        name = "Buzz",
                        tagline = "A Real Bitter Experience.",
                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                        imageUrl = "https://images.punkapi.com/v2/keg.png"
                    )
                ),
                loadNextPage = false
            ),
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}