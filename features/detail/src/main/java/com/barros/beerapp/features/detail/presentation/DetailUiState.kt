package com.barros.beerapp.features.detail.presentation

import com.barros.beerapp.libraries.beer.domain.model.BeerModel

internal sealed class DetailUiState {
    data object Loading : DetailUiState()
    data object Error : DetailUiState()
    data class ShowBeer(val beer: BeerModel) : DetailUiState()
}
