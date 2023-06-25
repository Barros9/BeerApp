package com.barros.beerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barros.beerapp.features.detail.presentation.DetailScreen
import com.barros.beerapp.features.home.presentation.HomeScreen
import com.barros.beerapp.libraries.navigator.destinations.DetailDestination
import com.barros.beerapp.libraries.navigator.destinations.HomeDestination
import com.barros.beerapp.libraries.navigator.navigation.NavigationDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import com.barros.beerapp.libraries.navigator.navigation.NavigatorEvent
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            BeerAppTheme {
                LaunchedEffect(navController) {
                    navigator.destinations.collectLatest {
                        when (val event = it) {
                            is NavigatorEvent.NavigateUp -> navController.navigateUp()
                            is NavigatorEvent.PopBackStack -> navController.popBackStack()
                            is NavigatorEvent.Directions -> navController.navigate(
                                event.destination,
                                event.builder
                            )
                        }
                    }
                }

                val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> =
                    mapOf(
                        HomeDestination to { HomeScreen() },
                        DetailDestination to { DetailScreen() }
                    )

                NavHost(navController = navController, startDestination = HomeDestination.route()) {
                    composableDestinations.forEach { entry ->
                        val destination = entry.key
                        composable(destination.route(), destination.arguments) {
                            entry.value()
                        }
                    }
                }
            }
        }
    }
}
