package com.barros.beerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.barros.beerapp.features.detail.presentation.DetailNavigation
import com.barros.beerapp.features.detail.presentation.detailScreen
import com.barros.beerapp.features.home.presentation.HomeNavigation
import com.barros.beerapp.features.home.presentation.homeScreen
import com.barros.beerapp.libraries.ui.theme.BeerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val theme by viewModel.theme.collectAsStateWithLifecycle()
            if (theme is ThemeUiModel.Loading) return@setContent

            BeerAppTheme(theme is ThemeUiModel.Dark) {
                NavHost(navController = navController, startDestination = HomeNavigation) {
                    homeScreen(
                        onSelectBeer = { beerId -> navController.navigate(DetailNavigation(beerId)) }
                    )
                    detailScreen(
                        onNavigateUp = navController::navigateUp
                    )
                }
            }
        }
    }
}
