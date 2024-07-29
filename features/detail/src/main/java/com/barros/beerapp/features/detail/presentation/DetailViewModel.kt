package com.barros.beerapp.features.detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeerByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBeerByIdUseCase: GetBeerByIdUseCase
) : ViewModel() {

    private val beerId = savedStateHandle.toRoute<DetailNavigation>().beerId

    private val _uiState by lazy { mutableStateOf<DetailUiState>(DetailUiState.Loading) }
    internal val uiState: State<DetailUiState> by lazy { _uiState.apply { loadUiState() } }

    private fun loadUiState() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            _uiState.value = when (val response = getBeerByIdUseCase(beerId)) {
                is Result.Success -> DetailUiState.ShowBeer(response.data)
                is Result.Error -> DetailUiState.Error
            }
        }
    }

    fun onRetry() {
        loadUiState()
    }
}
