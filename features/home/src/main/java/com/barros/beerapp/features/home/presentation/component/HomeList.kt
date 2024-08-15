package com.barros.beerapp.features.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.features.home.R
import com.barros.beerapp.features.home.presentation.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.barros.beerapp.libraries.ui.R as R_UI

@Composable
internal fun HomeList(
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    onSelectBeer: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is HomeUiState.Empty -> {
                Text(stringResource(R.string.home_empty))
            }

            is HomeUiState.Error -> {
                Text(stringResource(R.string.home_retry_error))
                Button(onClick = { onRetry() }) {
                    Text(stringResource(R.string.home_retry))
                }
            }

            is HomeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is HomeUiState.Success -> {
                val listState = rememberLazyListState()
                val endOfListReached by remember {
                    derivedStateOf { listState.lastScrolledForward }
                }

                LaunchedEffect(endOfListReached) {
                    if (endOfListReached && isLoadingNextPage.not() && isPaginationExhaust.not()) {
                        onSearchNextPage()
                    }
                }

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    state = listState
                ) {
                    items(uiState.beers) { beer ->
                        BeerRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(R_UI.dimen.item_height))
                                .padding(dimensionResource(R_UI.dimen.spacing_16)),
                            beer = beer,
                            onSelectBeer = { onSelectBeer(it) }
                        )
                    }
                    if (isLoadingNextPage) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(com.barros.beerapp.libraries.ui.R.dimen.spacing_16))
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListPreviewLoading() {
    BeerAppTheme {
        HomeList(
            modifier = Modifier,
            uiState = HomeUiState.Loading,
            isLoadingNextPage = false,
            isPaginationExhaust = false,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListPreviewEmpty() {
    BeerAppTheme {
        HomeList(
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

@Preview(showBackground = true)
@Composable
private fun HomeListPreviewError() {
    BeerAppTheme {
        HomeList(
            modifier = Modifier,
            uiState = HomeUiState.Error,
            isLoadingNextPage = false,
            isPaginationExhaust = false,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListPreviewShowBeers() {
    BeerAppTheme {
        HomeList(
            modifier = Modifier,
            uiState = HomeUiState.Success(
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
                )
            ),
            isLoadingNextPage = false,
            isPaginationExhaust = false,
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {}
        )
    }
}
