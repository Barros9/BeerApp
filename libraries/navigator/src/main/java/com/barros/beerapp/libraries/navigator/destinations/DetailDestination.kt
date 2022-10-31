package com.barros.beerapp.libraries.navigator.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.barros.beerapp.libraries.navigator.navigation.NavigationDestination

object DetailDestination : NavigationDestination {

    const val BEER_ID_PARAM = "beerId"
    private const val DETAIL_ROUTE_PARAM = "detailRoute"
    private const val DETAIL_ROUTE = "$DETAIL_ROUTE_PARAM/{$BEER_ID_PARAM}"

    override fun route(): String = DETAIL_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(BEER_ID_PARAM) { type = NavType.IntType })

    fun createBeerDetailsRoute(beerId: Int) = "$DETAIL_ROUTE_PARAM/$beerId"
}
