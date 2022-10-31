package com.barros.beerapp.features.detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.features.detail.presentation.model.DetailUiState
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeerByIdUseCase
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination.BEER_ID_PARAM
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle,
    private val getBeerByIdUseCase: GetBeerByIdUseCase,
) : ViewModel() {

    private val beerId
        get() = savedStateHandle.get<Int>(BEER_ID_PARAM)
            ?: throw IllegalStateException("Parameter beerId must not be null!")

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

    fun navigateUp() {
        navigator.navigateUp()
    }
}
