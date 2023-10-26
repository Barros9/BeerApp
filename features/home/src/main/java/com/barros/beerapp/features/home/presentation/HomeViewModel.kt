package com.barros.beerapp.features.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.features.home.presentation.model.HomeUiState
import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result.Error
import com.barros.beerapp.libraries.beer.domain.model.Result.Success
import com.barros.beerapp.libraries.beer.domain.usecase.GetBeersUseCase
import com.barros.beerapp.libraries.beer.domain.util.MAX_ITEM_PER_PAGE
import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.domain.usecase.SaveThemePreferenceUseCase
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getBeersUseCase: GetBeersUseCase,
    private val saveThemePreferenceUseCase: SaveThemePreferenceUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = HomeUiState.Error
    }

    private val _uiState by lazy { mutableStateOf<HomeUiState>(HomeUiState.Loading) }
    internal val uiState: State<HomeUiState> by lazy { _uiState }

    private val _isLoadingNextPage by lazy { mutableStateOf(false) }
    internal val isLoadingNextPage: State<Boolean> by lazy { _isLoadingNextPage }

    private val _isPaginationExhaust by lazy { mutableStateOf(false) }
    internal val isPaginationExhaust: State<Boolean> by lazy { _isPaginationExhaust }

    private val _search by lazy { MutableStateFlow("") }
    internal val search: StateFlow<String> by lazy { _search }

    private val beers = mutableSetOf<Beer>()
    private var page = 1

    init {
        viewModelScope.launch {
            _search.debounce(1_000).collectLatest { _ ->
                startFromBegin()
            }
        }
    }

    private fun loadUiState() {
        viewModelScope.launch(exceptionHandler) {
            if (page == 1 || _isPaginationExhaust.value.not()) {
                showLoading()
                getBeersUseCase(search = _search.value, page = page).collectLatest { result ->
                    _uiState.value = when (result) {
                        is Error -> HomeUiState.Error
                        is Success -> {
                            beers.addAll(result.data)

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
        navigator.navigate(
            DetailDestination.createBeerDetailsRoute(beerId = beerId)
        )
    }

    fun onRetry() {
        loadUiState()
    }

    fun onSearchNextPage() {
        loadUiState()
    }

    fun onSelectTheme(theme: Theme) {
        viewModelScope.launch {
            saveThemePreferenceUseCase(theme)
        }
    }

    fun onSearchTextChange(text: String) {
        _search.update { text }
    }

    private fun startFromBegin() {
        page = 1
        beers.clear()
        loadUiState()
    }

    private fun showLoading() {
        if (page == 1) {
            _uiState.value = HomeUiState.Loading
        } else {
            _isLoadingNextPage.value = true
        }
    }
}
