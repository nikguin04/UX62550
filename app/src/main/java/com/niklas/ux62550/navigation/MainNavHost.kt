package com.niklas.ux62550.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.niklas.ux62550.data.examples.MediaDetailExample
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.ui.feature.home.HomeScreen
import com.niklas.ux62550.ui.feature.mediadetails.MediaDetailsScreen
import com.niklas.ux62550.ui.feature.profile.LoginRegisterScreen
import com.niklas.ux62550.ui.feature.profile.LoginScreen
import com.niklas.ux62550.ui.feature.profile.ProfileScreen
import com.niklas.ux62550.ui.feature.profile.RegisterScreen
import com.niklas.ux62550.ui.feature.review.ReviewScreen
import com.niklas.ux62550.ui.feature.search.SearchScreen
import com.niklas.ux62550.ui.feature.watchlist.WatchlistScreen

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

                onNavigateToMedia = { media ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                    navController.navigate(Route.MediaDetailsScreen)
                }
            )
        }

        composable<Route.SearchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.SearchScreen>()) }
            /*SearchScreen(
                onNavigateToMedia = { name -> navController.navigate(Route.MediaDetailsScreen(name)) }
            )*/
        }

        composable<Route.MediaDetailsScreen> { backStackEntry ->
            //val mediaRoute: Route.MediaDetailsScreen = backStackEntry.toRoute()
            //val media = mediaRoute.media
            val media = navController.previousBackStackEntry?.savedStateHandle?.get<MediaObject>("media")
            LaunchedEffect(Unit) { /*onRouteChanged(backStackEntry.toRoute<Route.MediaDetailsScreen>())*/ }
            MediaDetailsScreen(
                media?: SearchDataExamples.MediaObjectExample,
                onNavigateToReview = { mediaDetails ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("reviewMedia", mediaDetails)
                    navController.navigate(Route.ReviewScreen) },
                onNavigateToOtherMedia = { newmedia ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", newmedia)
                    navController.navigate(Route.MediaDetailsScreen) }
                )
        }

        composable<Route.ReviewScreen> { backStackEntry ->
            val media = navController.previousBackStackEntry?.savedStateHandle?.get<MovieDetailObject>("reviewMedia")
            LaunchedEffect(Unit) { onRouteChanged(backStackEntry.toRoute<Route.ReviewScreen>()) }
            ReviewScreen(media = media?: MediaDetailExample.MediaDetailObjectExample)
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
            LoginScreen(onNavigateToProfile = { navController.navigateAndClearBackStack(Route.ProfileScreen) })
        }

        composable<Route.RegisterScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.RegisterScreen>()) }
            RegisterScreen(onNavigateToProfile = { navController.navigateAndClearBackStack(Route.ProfileScreen) })
        }

        composable<Route.ProfileScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.ProfileScreen>()) }
            ProfileScreen(onNavigateToLoginRegister = { navController.navigateAndClearBackStack(Route.LoginRegisterScreen) })
        }

        composable<Route.WatchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.WatchScreen>()) }
            //WatchlistScreen(onNavigateToMedia = { name -> navController.navigate(Route.MediaDetailsScreen(name)) })
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
