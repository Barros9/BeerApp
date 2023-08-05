package com.barros.beerapp

sealed class ThemeUiModel {
    object Loading: ThemeUiModel()
    object Dark: ThemeUiModel()
    object Light: ThemeUiModel()
}