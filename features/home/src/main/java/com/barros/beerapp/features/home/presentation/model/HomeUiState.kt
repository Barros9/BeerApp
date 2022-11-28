package com.barros.beerapp.features.home.presentation.model

import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal sealed class HomeUiState {
    object Empty : HomeUiState()
    object Loading : HomeUiState()
    object Error : HomeUiState()
    data class ShowBeers(val beers: List<Beer>, val loadNextPage: Boolean) : HomeUiState()
}
