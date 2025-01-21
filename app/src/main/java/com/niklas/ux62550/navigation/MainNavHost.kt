package com.niklas.ux62550.navigation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.niklas.ux62550.data.examples.MediaDetailExample
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.CreditsViewModelFactory
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.ui.feature.home.HomeScreen
import com.niklas.ux62550.ui.feature.mediadetails.MediaDetailsScreen
import com.niklas.ux62550.ui.feature.mediadetails.MovieViewModelFactory
import com.niklas.ux62550.ui.feature.profile.LoginRegisterScreen
import com.niklas.ux62550.ui.feature.profile.LoginScreen
import com.niklas.ux62550.ui.feature.profile.ProfileScreen
import com.niklas.ux62550.ui.feature.profile.RegisterScreen
import com.niklas.ux62550.ui.feature.review.ScreenReviewAndRating
import com.niklas.ux62550.ui.feature.search.SearchScreen
import com.niklas.ux62550.ui.feature.watchlist.WatchlistContent


@Composable
fun MainNavHost(
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit,
    snackbarShow: (String) -> Unit,
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
                modifier = Modifier.statusBarsPadding(),
                onNavigateToMedia = { media ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                    navController.navigate(Route.MediaDetailsScreen)
                }
            )
        }

        composable<Route.SearchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.SearchScreen>()) }
            SearchScreen(
                modifier = Modifier.statusBarsPadding(),
                onNavigateToMedia = { media ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                    navController.navigate(Route.MediaDetailsScreen)
                }
            )
        }

        composable<Route.MediaDetailsScreen> { backStackEntry ->
            //val mediaRoute: Route.MediaDetailsScreen = backStackEntry.toRoute()
            //val media = mediaRoute.media
            val media = getRelevantBackstackMedia<MediaObject>(navController, "media") ?: SearchDataExamples.MediaObjectExample // Default to media object example if no media as placeholder
            LaunchedEffect(Unit) { /*onRouteChanged(backStackEntry.toRoute<Route.MediaDetailsScreen>())*/ }
            MediaDetailsScreen(
                modifier = Modifier.statusBarsPadding(),
                movieViewModel = viewModel(factory = MovieViewModelFactory(media = media)),
                creditsViewModel = viewModel(factory = CreditsViewModelFactory(media = media)),
                navigateBack = navController::popBackStack,
                onNavigateToReview = { mediaDetails ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("reviewMedia", mediaDetails)
                    navController.navigate(Route.ReviewScreen) },
                onNavigateToOtherMedia = { newmedia ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", newmedia)
                    navController.navigate(Route.MediaDetailsScreen) }
            )
        }

        composable<Route.ReviewScreen> { backStackEntry ->
            val media = getRelevantBackstackMedia<MovieDetailObject>(navController, "reviewMedia")
            LaunchedEffect(Unit) { onRouteChanged(backStackEntry.toRoute<Route.ReviewScreen>()) }
            ScreenReviewAndRating(
                modifier = Modifier.statusBarsPadding(),
                media = media?: MediaDetailExample.MediaDetailObjectExample,
                navBack = navController::popBackStack,
                snackbarShow = snackbarShow
            )
        }

        composable<Route.LoginRegisterScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginRegisterScreen>()) }
            LoginRegisterScreen(
                modifier = Modifier.statusBarsPadding(),
                onNavigateToLoginScreen = { navController.navigate(Route.LoginScreen) },
                onNavigateToRegisterScreen = { navController.navigate(Route.RegisterScreen) }
            )
        }

        composable<Route.LoginScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginScreen>()) }
            LoginScreen(
            	modifier = Modifier.statusBarsPadding(),
                navigateBack = navController::popBackStack,
                onNavigateToProfile = { navController.navigateAndClearBackStack(Route.ProfileScreen) }
            )
        }

        composable<Route.RegisterScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.RegisterScreen>()) }
            RegisterScreen(
                modifier = Modifier.statusBarsPadding(),
                navigateBack = navController::popBackStack,
                onNavigateToProfile = { navController.navigateAndClearBackStack(Route.ProfileScreen) }
            )
        }

        composable<Route.ProfileScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.ProfileScreen>()) }
            ProfileScreen(
                modifier = Modifier.statusBarsPadding(),
                onNavigateToLoginRegister = { navController.navigateAndClearBackStack(Route.LoginRegisterScreen) }
            )
        }

        composable<Route.WatchScreen> {
            LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.WatchScreen>()) }
            WatchlistContent(
                modifier = Modifier.statusBarsPadding(),
                onNavigateToMedia = { media ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                    navController.navigate(Route.MediaDetailsScreen)
                }
            )
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

private fun <T> getRelevantBackstackMedia(navController: NavHostController, name: String): T? {
    val toReturn =
        navController.previousBackStackEntry?.savedStateHandle?.get<T>(name)?: // Current media when on screen
        navController.currentBackStackEntry?.savedStateHandle?.get<T>(name) // Current media when navigating back
    return toReturn
}