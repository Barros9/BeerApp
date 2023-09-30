package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.ui.R
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun BeerRow(
    beer: Beer,
    onSelectBeer: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onSelectBeer(beer.id) })
            .padding(dimensionResource(R.dimen.spacing_16)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.width(dimensionResource(R.dimen.row_image_width)),
            imageModel = { beer.imageUrl ?: "" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit
            ),
            previewPlaceholder = com.barros.beerapp.features.home.R.drawable.ic_loading,
            failure = { ImageVector.vectorResource(com.barros.beerapp.features.home.R.drawable.ic_broken_image) }
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
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    Divider(thickness = dimensionResource(R.dimen.divider))
}

@Preview(showBackground = true)
@Composable
private fun BeerRowPreview() {
    BeerAppTheme {
        BeerRow(
            beer = Beer(
                id = 1,
                name = "Buzz",
                tagline = "A Real Bitter Experience.",
                description = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
                imageUrl = "https://images.punkapi.com/v2/keg.png"
            )
        )
    }
}
