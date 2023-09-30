package com.barros.beerapp.features.detail.presentation.model

import com.barros.beerapp.libraries.beer.domain.entity.Beer

internal sealed class DetailUiState {
    data object Loading : DetailUiState()
    data object Error : DetailUiState()
    data class ShowBeer(val beer: Beer) : DetailUiState()
}
