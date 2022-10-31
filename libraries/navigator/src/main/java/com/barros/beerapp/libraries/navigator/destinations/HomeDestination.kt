package com.barros.beerapp.libraries.navigator.destinations

import com.barros.beerapp.libraries.navigator.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    private const val HOME_ROUTE = "homeRoute"
    override fun route(): String = HOME_ROUTE
}
