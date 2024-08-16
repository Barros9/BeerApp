package com.barros.beerapp.features.home.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.barros.beerapp.features.home.R
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import com.barros.beerapp.libraries.ui.theme.Shapes

@Composable
internal fun HomeSearch(
    search: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .testTag("search"),
        shape = Shapes.medium,
        singleLine = true,
        maxLines = 1,
        placeholder = { Text(stringResource(id = R.string.home_search_message)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { keyboardController?.hide() }
        ),
        value = search,
        onValueChange = { text -> onSearchChange(text) }
    )
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    BeerAppTheme {
        HomeSearch(
            modifier = Modifier,
            search = "",
            onSearchChange = {}
        )
    }
}
