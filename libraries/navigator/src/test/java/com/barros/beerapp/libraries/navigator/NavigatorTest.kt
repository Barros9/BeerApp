package com.barros.beerapp.libraries.navigator

import app.cash.turbine.test
import com.barros.beerapp.libraries.navigator.destinations.HomeDestination
import com.barros.beerapp.libraries.navigator.navigation.Navigator
import com.barros.beerapp.libraries.navigator.navigation.NavigatorEvent
import com.barros.beerapp.libraries.navigator.navigation.NavigatorImpl
import com.barros.beerapp.libraries.navigator.navigation.NavigatorViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NavigatorTest {

    private lateinit var navigator: Navigator
    private lateinit var navigatorViewModel: NavigatorViewModel

    @Before
    fun setup() {
        navigator = NavigatorImpl()
        navigatorViewModel = NavigatorViewModel(navigator)
    }

    @Test
    fun `navigate to home with success`() = runTest {
        // Given

        // When
        navigator.destinations.test {
            navigatorViewModel.navigate(HomeDestination.route()) { popUpTo(0) }
            val result = awaitItem()

            // Then
            assert(result is NavigatorEvent.Directions)
            assertEquals(HomeDestination.route(), (result as NavigatorEvent.Directions).destination)
        }
    }

    @Test
    fun `navigateUp with success`() = runTest {
        // Given

        // When
        navigator.destinations.test {
            navigatorViewModel.navigateUp()
            val result = awaitItem()

            // Then
            assert(result is NavigatorEvent.NavigateUp)
        }
    }

    @Test
    fun `popBackStack with success`() = runTest {
        // Given

        // When
        navigator.destinations.test {
            navigatorViewModel.popBackStack()
            val result = awaitItem()

            // Then
            assert(result is NavigatorEvent.PopBackStack)
        }
    }
}
