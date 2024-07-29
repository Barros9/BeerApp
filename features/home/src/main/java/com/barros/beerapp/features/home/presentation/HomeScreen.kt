package com.barros.beerapp.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barros.beerapp.features.home.BuildConfig
import com.barros.beerapp.features.home.R
import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme

@Composable
internal fun HomeScreen(
    onSelectBeer: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState
    val isLoadingNextPage by homeViewModel.isLoadingNextPage
    val isPaginationExhaust by homeViewModel.isPaginationExhaust
    val search by homeViewModel.search.collectAsStateWithLifecycle()

    HomeContent(
        modifier = Modifier,
        uiState = uiState,
        isLoadingNextPage = isLoadingNextPage,
        isPaginationExhaust = isPaginationExhaust,
        search = search,
        onSearchChange = { text -> homeViewModel.onSearchTextChange(text) },
        onSelectBeer = { beerId -> onSelectBeer(beerId) },
        onRetry = { homeViewModel.onRetry() },
        onSearchNextPage = { homeViewModel.onSearchNextPage() },
        onSelectTheme = { homeViewModel.onSelectTheme(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState,
    isLoadingNextPage: Boolean,
    isPaginationExhaust: Boolean,
    search: String,
    onSearchChange: (String) -> Unit,
    onSelectBeer: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchNextPage: () -> Unit,
    onSelectTheme: (Theme) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val showInfoDialog = remember { mutableStateOf(false) }

    if (showInfoDialog.value) {
        AlertDialog(
            icon = { Icon(Icons.Filled.Face, contentDescription = null) },
            title = { Text(text = stringResource(id = R.string.home_info_title)) },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.home_info_message, BuildConfig.VERSION_NAME),
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = { showInfoDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = { showInfoDialog.value = false }
                ) {
                    Text(stringResource(id = R.string.home_ok))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displayMedium,
                        text = stringResource(R.string.home_title),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.home_light_mode)) },
                            onClick = {
                                onSelectTheme(Theme.Light)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.home_dark_mode)) },
                            onClick = {
                                onSelectTheme(Theme.Dark)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.home_info_title)) },
                            onClick = {
                                showInfoDialog.value = true
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeHeader(modifier = modifier)

            HomeSearch(
                modifier = modifier,
                search = search,
                onSearchChange = onSearchChange
            )

            HomeList(
                modifier = modifier,
                uiState = uiState,
                isLoadingNextPage = isLoadingNextPage,
                isPaginationExhaust = isPaginationExhaust,
                onSelectBeer = onSelectBeer,
                onRetry = onRetry,
                onSearchNextPage = onSearchNextPage
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreviewEmpty() {
    BeerAppTheme {
        HomeContent(
            modifier = Modifier,
            uiState = HomeUiState.Empty,
            isLoadingNextPage = false,
            isPaginationExhaust = false,
            search = "",
            onSearchChange = {},
            onSelectBeer = {},
            onRetry = {},
            onSearchNextPage = {},
            onSelectTheme = {}
        )
    }
}
