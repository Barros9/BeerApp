package com.barros.beerapp

sealed class ThemeUiModel {
    data object Loading : ThemeUiModel()
    data object Dark : ThemeUiModel()
    data object Light : ThemeUiModel()
}
