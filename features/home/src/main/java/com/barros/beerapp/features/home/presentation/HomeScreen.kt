@file:OptIn(ExperimentalMaterial3Api::class)

package com.barros.beerapp.features.home.presentation

import com.barros.beerapp.libraries.ui.R as R_UI
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.barros.beerapp.features.home.R
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.uiState

    HomeContent(
        modifier = Modifier,
        uiState = uiState,
        onSelectedBeer = { beerId -> homeViewModel.onSelectedBeer(beerId) },
        onRetry = { homeViewModel.onRetry() }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onSelectedBeer: (Int) -> Unit,
    onRetry: () -> Unit,
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
                    val beers = uiState.beers.collectAsLazyPagingItems()
                    LazyColumn {
                        items(beers) { beer ->
                            BeerRow(
                                beer = beer!!,
                                onSelectedBeer = { onSelectedBeer(it) }
                            )
                        }
                        beers.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        LoadingView(
                                            modifier = Modifier.fillParentMaxSize()
                                        )
                                    }
                                }
                                loadState.append is LoadState.Loading -> {
                                    item {
                                        LoadingItem()
                                    }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    item {
                                        ErrorItem(
                                            modifier = Modifier.fillParentMaxSize(),
                                            onClickRetry = { retry() }
                                        )
                                    }
                                }
                                loadState.append is LoadState.Error -> {
                                    item {
                                        ErrorItem(
                                            onClickRetry = { retry() }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BeerRow(
    beer: Beer,
    onSelectedBeer: (Int) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onSelectedBeer(beer.id) })
            .padding(dimensionResource(R_UI.dimen.spacing_16)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.width(dimensionResource(R_UI.dimen.row_image_width)),
            imageModel = { beer.imageUrl ?: "" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit
            ),
            previewPlaceholder = R_UI.drawable.ic_loading,
            failure = { ImageVector.vectorResource(R_UI.drawable.ic_broken_image) }
        )

        Column(
            modifier = Modifier
                .padding(start = dimensionResource(R_UI.dimen.spacing_24))
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = beer.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                Text(
                    text = beer.tagline,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            ) {
                Text(
                    modifier = Modifier.padding(top = dimensionResource(R_UI.dimen.spacing_8)),
                    text = beer.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    Divider(thickness = dimensionResource(R_UI.dimen.divider))
}

@Composable
private fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun ErrorItem(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.home_retry_error),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.home_retry))
        }
    }
}
