package com.barros.beerapp.features.home.presentation

import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal sealed class HomeUiState {
    data object Empty : HomeUiState()
    data object Error : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val beers: List<Beer>) : HomeUiState()
}
