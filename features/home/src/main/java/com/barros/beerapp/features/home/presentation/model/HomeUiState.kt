package com.barros.beerapp.features.home.presentation.model

import androidx.paging.PagingData
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import kotlinx.coroutines.flow.Flow

internal sealed class HomeUiState {
    object Loading : HomeUiState()
    object Error : HomeUiState()
    data class ShowBeers(val beers: Flow<PagingData<Beer>>) : HomeUiState()
}
