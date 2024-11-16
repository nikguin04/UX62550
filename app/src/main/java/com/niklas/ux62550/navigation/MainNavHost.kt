package com.niklas.ux62550.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MovieBox
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.ui.screen_home.HomeScreen
import com.niklas.ux62550.ui.screen_mediadetails.MediaDetailsScreen
import com.niklas.ux62550.ui.screen_profile.LoginRegisterScreen
import com.niklas.ux62550.ui.screen_profile.LoginScreen
import com.niklas.ux62550.ui.screen_profile.ProfileScreen
import com.niklas.ux62550.ui.screen_profile.RegisterScreen
import com.niklas.ux62550.ui.screen_search.MovieBoxItemsUIState
import com.niklas.ux62550.ui.screen_search.NonMovieBoxItemsUIState
import com.niklas.ux62550.ui.screen_search.ScreenSearch
import kotlin.collections.listOf

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
                onNavigateToMedia = { name ->
                    navController.navigate(Route.MediaDetailsScreen(name))
                }
            )
        }

        composable<Route.SearchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.HomeScreen>()) }
            ScreenSearch(
                // TODO: more mock data because no viewmodel >:(
                movieBoxItemsUIState = MovieBoxItemsUIState.Data(listOf(
                    MovieBox("Name 1", R.drawable.logo, Color.Blue, "Movie", 3.5f),
                    MovieBox("Name 2", R.drawable.logo, Color.Red, "Series", 4.5f)
                )),
                nonMovieBoxItemsUIState = NonMovieBoxItemsUIState.Data(listOf(
                    NonMovieBox("someActor", R.drawable.logo, Color.Yellow, "Movie"),
                    NonMovieBox("someGenre", R.drawable.logo, Color.Green, "Series")
                )),
                onNavigateToMedia = { name ->
                    navController.navigate(Route.MediaDetailsScreen(name))
                }
            )
        }

        composable<Route.MediaDetailsScreen> { backStackEntry ->
            LaunchedEffect(Unit) { onRouteChanged(backStackEntry.toRoute<Route.MediaDetailsScreen>()) }
            MediaDetailsScreen(
                onNavigateToOtherMedia = { name -> navController.navigate(Route.MediaDetailsScreen(name)) }
            )
        }

        composable<Route.LoginRegisterScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginRegisterScreen>()) }
            LoginRegisterScreen(
                onNavigateToLoginScreen = { navController.navigate(Route.LoginScreen) },
                onNavigateToRegisterScreen = { navController.navigate(Route.RegisterScreen) }
            )
        }

        composable<Route.LoginScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginScreen>()) }
            LoginScreen()
        }

        composable<Route.RegisterScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.RegisterScreen>()) }
            RegisterScreen()
        }

        composable<Route.ProfileScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.ProfileScreen>()) }
            ProfileScreen(onNavigateToLoginRegister = { navController.navigateAndClearBackStack(Route.LoginRegisterScreen) })
        }

        composable<Route.SearchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.SearchScreen>()) }
            //ScreenSearch(.... wtf?)
        }

        composable<Route.WatchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.RegisterScreen>()) }
            //ScreenWachlist(.... wtf)
        }
    }
}

fun NavHostController.navigateAndClearBackStack(route: Route) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = true // This removes the start destination from the backstack
        }
        // Set launchSingleTop to prevent multiple copies of the same destination on the backstack
        launchSingleTop = true
    }
}
