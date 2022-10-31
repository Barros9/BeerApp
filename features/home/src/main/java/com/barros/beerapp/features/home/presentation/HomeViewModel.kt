package com.barros.beerapp.features.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getBeersUseCase: GetBeersUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = HomeUiState.Error
    }

    private val _uiState by lazy { mutableStateOf<HomeUiState>(HomeUiState.Loading) }
    internal val uiState: State<HomeUiState> by lazy { _uiState.apply { loadUiState() } }

    private fun loadUiState() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = HomeUiState.Loading
            _uiState.value = HomeUiState.ShowBeers(getBeersUseCase())
        }
    }

    fun onSelectedBeer(beerId: Int) {
        navigator.navigate(DetailDestination.createBeerDetailsRoute(beerId = beerId))
    }

    fun onRetry() {
        loadUiState()
    }
}
