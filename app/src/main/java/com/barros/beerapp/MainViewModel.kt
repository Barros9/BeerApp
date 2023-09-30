package com.barros.beerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.libraries.domain.entity.Theme
import com.barros.beerapp.libraries.domain.usecase.SubscribeOnThemePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    subscribeOnThemePreferenceUseCase: SubscribeOnThemePreferenceUseCase
) : ViewModel() {

    val theme: StateFlow<ThemeUiModel> =
        subscribeOnThemePreferenceUseCase()
            .map {
                when (it) {
                    Theme.Light -> ThemeUiModel.Light
                    Theme.Dark -> ThemeUiModel.Dark
                }
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = ThemeUiModel.Loading,
                started = SharingStarted.WhileSubscribed(5_000)
            )
}
