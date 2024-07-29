package com.barros.beerapp.features.detail.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class DetailNavigation(val beerId: Int)

fun NavGraphBuilder.detailScreen(
    onNavigateUp: () -> Unit
) {
    composable<DetailNavigation> {
        DetailScreen(onNavigateUp = onNavigateUp)
    }
}