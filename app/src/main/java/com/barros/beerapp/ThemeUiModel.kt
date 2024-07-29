package com.barros.beerapp

internal sealed class ThemeUiModel {
    data object Loading : ThemeUiModel()
    data object Dark : ThemeUiModel()
    data object Light : ThemeUiModel()
}
