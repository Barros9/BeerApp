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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.features.home.R
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme

@Composable
internal fun HomeList(
    modifier: Modifier,
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    onSelectBeer: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchNextPage: () -> Unit
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

                LaunchedEffect(listState.canScrollForward.not()) {
                    if (listState.canScrollForward.not() && isLoadingNextPage.not() && isPaginationExhaust.not()) {
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
                    if (isPaginationExhaust) {
                        // TODO update this
//                        item {
//                            Text(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(dimensionResource(com.barros.beerapp.libraries.ui.R.dimen.spacing_16))
//                                    .wrapContentWidth(Alignment.CenterHorizontally),
//                                text = "isPaginationExhaust"
//                            )
//                        }
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
                    )
//                    Beer(
//                        id = 0,
//                        name = "Buzz",
//                        tagline = "A Real Bitter Experience.",
//                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
//                        imageUrl = "https://images.punkapi.com/v2/keg.png"
//                    ),
//                    Beer(
//                        id = 0,
//                        name = "Buzz",
//                        tagline = "A Real Bitter Experience.",
//                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
//                        imageUrl = "https://images.punkapi.com/v2/keg.png"
//                    ),
//                    Beer(
//                        id = 0,
//                        name = "Buzz",
//                        tagline = "A Real Bitter Experience.",
//                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
//                        imageUrl = "https://images.punkapi.com/v2/keg.png"
//                    ),
//                    Beer(
//                        id = 0,
//                        name = "Buzz",
//                        tagline = "A Real Bitter Experience.",
//                        description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
//                        imageUrl = "https://images.punkapi.com/v2/keg.png"
//                    )
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
