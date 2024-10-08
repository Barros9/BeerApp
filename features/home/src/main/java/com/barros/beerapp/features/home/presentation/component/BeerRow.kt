package com.barros.beerapp.features.home.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.libraries.beer.domain.BeerFake.buzzBeerModel
import com.barros.beerapp.libraries.beer.domain.model.BeerModel
import com.barros.beerapp.libraries.ui.R
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun BeerRow(
    beer: BeerModel,
    onSelectBeer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onSelectBeer(beer.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.width(dimensionResource(R.dimen.row_image_width)),
            model = beer.imageUrl,
            contentDescription = null,
            loading = placeholder(R.drawable.ic_loading),
            failure = placeholder(R.drawable.ic_beer),
        )

        Column(
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen.spacing_24))
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
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_8)),
                    text = beer.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    HorizontalDivider(thickness = dimensionResource(R.dimen.divider))
}

@Preview(showBackground = true)
@Composable
private fun BeerRowPreview() {
    BeerAppTheme {
        BeerRow(
            modifier = Modifier,
            beer = buzzBeerModel,
            onSelectBeer = {}
        )
    }
}
