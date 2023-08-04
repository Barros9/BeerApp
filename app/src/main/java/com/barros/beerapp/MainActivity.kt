package com.barros.beerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barros.beerapp.features.detail.presentation.DetailScreen
import com.barros.beerapp.features.home.presentation.HomeScreen
import com.barros.beerapp.libraries.domain.entity.Theme
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

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val theme by viewModel.theme.collectAsStateWithLifecycle()

            val isDarkTheme = when (theme) {
                Theme.Dark -> true
                Theme.Light -> false
            }

            BeerAppTheme(isDarkTheme) {
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
