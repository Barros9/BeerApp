package com.barros.beerapp.features.home.presentation.model

import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal sealed class HomeUiState {
    object Empty : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val beers: List<Beer>) : HomeUiState()
}
