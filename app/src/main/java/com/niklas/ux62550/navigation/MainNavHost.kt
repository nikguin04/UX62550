package com.niklas.ux62550.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.niklas.ux62550.ui.screen_home.HomeScreen
import com.niklas.ux62550.ui.screen_profile.LoginRegisterScreen
import com.niklas.ux62550.ui.screen_profile.LoginScreen

// Step1: define routes ✅

// Step2: get nav controller ✅

// Step3: call NavHost ✅

// Step4: add screens to nav graph ✅

// Step5: add navigation actions ✅

// Step6: add navigation arguments ✅

// Step7: add top app bar with back arrow and screen title ✅

@Composable
fun MainNavHost(
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit,
    modifier: Modifier
) {
    ScreenWithGeneralNavBar(navController) {
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
                    name = backStackEntry.toRoute<Route.MediaDetailsScreen>().md_name,
                    //onNavigateToGreenScreen = { navController.navigate(Route.GreenScreen) }
                )
            }

            composable<Route.LoginRegisterScreen> {
                LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginRegisterScreen>()) }

                LoginRegisterScreen(
                    onNavigateToLoginScreen = { navController.navigate(Route.LoginScreen) },
                    onNavigateToRegisterScreen = { /*navController.navigate(Route.RegisterScreen)*/ }
                )
            }

            composable<Route.LoginScreen> {
                LaunchedEffect(Unit) { onRouteChanged(it.toRoute<Route.LoginScreen>()) }

                LoginScreen()
            }
        }
    }
}

@Composable
fun MediaDetailsScreen(name: Any) {
    Text(text = "THIS IS A TEMPORARY PLACEHOLDER!!!")
}
