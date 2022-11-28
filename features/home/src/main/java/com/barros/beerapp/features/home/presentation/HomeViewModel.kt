package com.barros.beerapp.features.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result.Error
import com.barros.beerapp.libraries.beer.domain.model.Result.Success
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersPagingUseCase
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getBeersPagingUseCase: GetBeersPagingUseCase,
    private val getBeersUseCase: GetBeersUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = HomeUiState.Error
    }

    private val _uiState by lazy { mutableStateOf<HomeUiState>(HomeUiState.Loading) }
    internal val uiState: State<HomeUiState> by lazy { _uiState.apply { loadUiState() } }

    private val beers = mutableListOf<Beer>()
    private var page = 1

    private fun loadUiStatePaging() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = HomeUiState.Loading
            _uiState.value = HomeUiState.ShowBeersPaging(getBeersPagingUseCase())
        }
    }

    private fun loadUiState() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = HomeUiState.Loading
            loadList()
        }
    }

    private fun loadList() {
        viewModelScope.launch(exceptionHandler) {
            getBeersUseCase(page = page).collect { result ->
                _uiState.value = when (result) {
                    is Success -> {
                        beers.addAll(result.data)
                        if (beers.isEmpty() && result.data.isEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.ShowBeers(beers = beers, loadNextPage = false)
                        }
                    }
                    is Error -> HomeUiState.Error
                }
            }
        }
    }

    fun searchNextPage() {
        viewModelScope.launch(exceptionHandler) {
            if (_uiState.value is HomeUiState.ShowBeers && (_uiState.value as HomeUiState.ShowBeers).loadNextPage.not()) {
                page++
                _uiState.value = HomeUiState.ShowBeers(beers = beers, loadNextPage = true)
                delay(1_000)
                loadList()
            }
        }
    }

    fun onSelectBeer(beerId: Int) {
        navigator.navigate(DetailDestination.createBeerDetailsRoute(beerId = beerId))
    }

    fun onRetry() {
        loadUiState()
    }
}
