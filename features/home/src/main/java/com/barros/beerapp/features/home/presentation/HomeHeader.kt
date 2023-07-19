package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.features.home.R
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme

@Composable
internal fun HomeHeader(
    modifier: Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
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
                .padding(dimensionResource(com.barros.beerapp.libraries.ui.R.dimen.spacing_8))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(com.barros.beerapp.libraries.ui.R.dimen.spacing_16)),
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
                    modifier = Modifier.size(dimensionResource(com.barros.beerapp.libraries.ui.R.dimen.beer_logo_size)),
                    painter = painterResource(com.barros.beerapp.libraries.ui.R.drawable.ic_beer),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    BeerAppTheme {
        HomeHeader(modifier = Modifier)
    }
}