package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.displayMedium,
                    text = stringResource(R.string.home_title),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R_UI.dimen.spacing_8))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R_UI.dimen.spacing_16)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.tertiary,
                                text = stringResource(R.string.home_card_title)
                            )
                            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                                Text(
                                    text = stringResource(R.string.home_card_subtitle),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                        Image(
                            modifier = Modifier.size(dimensionResource(R_UI.dimen.beer_logo_size)),
                            painter = painterResource(R_UI.drawable.ic_beer),
                            contentDescription = null
                        )
                    }
                }
            }
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

                    LazyColumn(state = listState) {
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
                                        .padding(dimensionResource(R_UI.dimen.spacing_16))
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        if (isPaginationExhaust) {
                            item {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R_UI.dimen.spacing_16))
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                    text = "isPaginationExhaust" // TODO update this
                                )
                            }
                        }
                    }
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
private fun HomeContentPreviewError() {
    BeerAppTheme {
        HomeContent(
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
private fun HomeContentPreviewShowBeers() {
    BeerAppTheme {
        HomeContent(
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
