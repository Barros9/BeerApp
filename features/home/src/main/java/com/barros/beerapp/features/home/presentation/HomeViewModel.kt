package com.barros.beerapp.features.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result.Error
import com.barros.beerapp.libraries.beer.domain.model.Result.Success
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.beer.domain.util.MAX_ITEM_PER_PAGE
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    // TODO try to use Success with copy
    private val _isLoadingNextPage by lazy { mutableStateOf(false) }
    internal val isLoadingNextPage: State<Boolean> by lazy { _isLoadingNextPage }

    // TODO try to use Success with copy
    private val _isPaginationExhaust by lazy { mutableStateOf(false) }
    internal val isPaginationExhaust: State<Boolean> by lazy { _isPaginationExhaust }

    private val beers = mutableSetOf<Beer>()
    private var page = 1

    private fun loadUiState() {
        viewModelScope.launch(exceptionHandler) {
            if (page == 1 || page != 1 && _isPaginationExhaust.value.not()) {
                if (page == 1) {
                    _uiState.value = HomeUiState.Loading
                } else {
                    _isLoadingNextPage.value = true
                }

                delay(1_000)

                getBeersUseCase(page = page)
                    .distinctUntilChanged()
                    .collectLatest { result ->
                        _uiState.value = when (result) {
                            is Error -> HomeUiState.Error
                            is Success -> {
                                if (page == 1) {
                                    beers.clear()
                                    beers.addAll(result.data)
                                } else {
                                    beers.addAll(result.data)
                                }

                                if (beers.isEmpty()) {
                                    HomeUiState.Empty
                                } else {
                                    _isPaginationExhaust.value = result.data.count() != MAX_ITEM_PER_PAGE
                                    _isLoadingNextPage.value = false
                                    if (_isPaginationExhaust.value.not()) page++
                                    HomeUiState.Success(beers = beers.toList())
                                }
                            }
                        }
                    }
            }
        }
    }

    fun onSelectBeer(beerId: Int) {
        navigator.navigate(DetailDestination.createBeerDetailsRoute(beerId = beerId))
    }

    fun onRetry() {
        loadUiState()
    }

    fun onSearchNextPage() {
        loadUiState()
    }
}
