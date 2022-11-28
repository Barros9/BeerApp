package com.barros.beerapp.features.home.presentation.model

import androidx.paging.PagingData
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import kotlinx.coroutines.flow.Flow

internal sealed class HomeUiState {
    object Empty : HomeUiState()
    object Loading : HomeUiState()
    object Error : HomeUiState()
    data class ShowBeersPaging(val beers: Flow<PagingData<Beer>>) : HomeUiState()
    data class ShowBeers(val beers: List<Beer>, val loadNextPage: Boolean) : HomeUiState()
}
