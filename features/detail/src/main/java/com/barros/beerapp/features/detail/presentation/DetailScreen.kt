package com.barros.beerapp.features.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.barros.beerapp.features.detail.R
import com.barros.beerapp.features.detail.presentation.model.DetailUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.barros.beerapp.libraries.ui.R as R_UI

@Composable
fun DetailScreen(detailViewModel: DetailViewModel = hiltViewModel()) {
    val uiState by detailViewModel.uiState

    DetailContent(
        modifier = Modifier,
        uiState = uiState,
        onNavigateUp = { detailViewModel.onNavigateUp() },
        onRetry = { detailViewModel.onRetry() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
                            imageVector = Icons.Filled.ArrowBack,
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
                    Button(onClick = { onRetry() }) {
                        Text(stringResource(R.string.detail_retry))
                    }
                }

                is DetailUiState.ShowBeer -> {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R_UI.dimen.spacing_24)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .width(dimensionResource(R_UI.dimen.detail_image_width))
                                .height(dimensionResource(R_UI.dimen.detail_image_height)),
                            imageModel = { uiState.beer.imageUrl ?: "" },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Fit
                            ),
                            loading = { ImageVector.vectorResource(R.drawable.ic_loading) },
                            failure = { ImageVector.vectorResource(R.drawable.ic_broken_image) }
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R_UI.dimen.spacing_16)))
                        Text(
                            text = uiState.beer.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R_UI.dimen.spacing_8)))
                        Text(
                            text = uiState.beer.tagline,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R_UI.dimen.spacing_8)))
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
                beer = Beer(
                    id = 0,
                    name = "Buzz",
                    tagline = "A Real Bitter Experience.",
                    description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                    imageUrl = "https://images.punkapi.com/v2/keg.png"
                )
            ),
            onNavigateUp = {},
            onRetry = {}
        )
    }
}
