package com.niklas.ux62550.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.niklas.ux62550.ui.screen_home.HomeScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen,
        modifier = modifier
    ) {
        composable<Route.HomeScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.HomeScreen>()) }

            HomeScreen(
                onNavigateToMediaDeatilsScreen = { name ->
                    navController.navigate(Route.MediaDetailsScreen(name))
                }
            )
        }

        composable<Route.MediaDetailsScreen> { backStackEntry ->
            LaunchedEffect(Unit) { onRouteChanged(backStackEntry.toRoute<Route.MediaDetailsScreen>()) }

            MediaDetailsScreen(
                name = backStackEntry.toRoute<Route.MediaDetailsScreen>().name,
                //onNavigateToGreenScreen = { navController.navigate(Route.GreenScreen) }
            )
        }
    }
}

@Composable
fun MediaDetailsScreen(name: String) {}
