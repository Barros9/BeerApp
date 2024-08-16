package com.barros.beerapp.features.home.presentation

import com.barros.beerapp.libraries.beer.domain.model.BeerModel

internal sealed class HomeUiState {
    data object Empty : HomeUiState()
    data object Error : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val beers: List<BeerModel>) : HomeUiState()
}
