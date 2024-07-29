package com.barros.beerapp.features.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation

fun NavGraphBuilder.homeScreen(
    onSelectBeer: (Int) -> Unit,
) {
    composable<HomeNavigation> {
        HomeScreen(onSelectBeer)
    }
}