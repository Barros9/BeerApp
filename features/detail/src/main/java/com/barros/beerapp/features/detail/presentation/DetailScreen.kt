package com.barros.beerapp.features.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.barros.beerapp.features.detail.R
import com.barros.beerapp.libraries.beer.domain.BeerFake.buzzBeerModel
import com.barros.beerapp.libraries.ui.R.dimen
import com.barros.beerapp.libraries.ui.R.drawable
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
internal fun DetailScreen(
    onNavigateUp: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by detailViewModel.uiState

    DetailContent(
        modifier = Modifier,
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onRetry = { detailViewModel.onRetry() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun DetailContent(
    modifier: Modifier,
    uiState: DetailUiState,
    onNavigateUp: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.detail_beer_top_app_bar_title))
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detail_go_back)
                        )
                    }
                }
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
                is DetailUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailUiState.Error -> {
                    Text(stringResource(R.string.detail_error))
                    Spacer(modifier = Modifier.height(dimensionResource(dimen.spacing_16)))
                    Button(onClick = { onRetry() }) {
                        Text(stringResource(R.string.detail_retry))
                    }
                }

                is DetailUiState.ShowBeer -> {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(dimensionResource(dimen.spacing_24)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .width(dimensionResource(dimen.detail_image_width))
                                .height(dimensionResource(dimen.detail_image_height)),
                            model = uiState.beer.imageUrl,
                            contentDescription = null,
                            loading = placeholder(R.drawable.ic_loading),
                            failure = placeholder(drawable.ic_beer),
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(dimen.spacing_16)))
                        Text(
                            text = uiState.beer.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(dimen.spacing_8)))
                        Text(
                            text = uiState.beer.tagline,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(dimen.spacing_16)))
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                            Text(
                                text = uiState.beer.description,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
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
private fun DetailContentPreviewLoading() {
    BeerAppTheme {
        DetailContent(
            modifier = Modifier,
            uiState = DetailUiState.Loading,
            onNavigateUp = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreviewError() {
    BeerAppTheme {
        DetailContent(
            modifier = Modifier,
            uiState = DetailUiState.Error,
            onNavigateUp = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreviewShowBeer() {
    BeerAppTheme {
        DetailContent(
            modifier = Modifier,
            uiState = DetailUiState.ShowBeer(
                beer = buzzBeerModel,
            ),
            onNavigateUp = {},
            onRetry = {}
        )
    }
}
