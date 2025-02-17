package com.niklas.ux62550.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val title: String) {
    @Serializable
    data object HomeScreen : Route("Home Screen")

    @Serializable
    data object SearchScreen : Route("Search Screen")

    @Serializable
    data object WatchScreen : Route("Watch")

    @Serializable
    data object MediaDetailsScreen : Route("Media Details Screen")

    @Serializable
    data object ReviewScreen : Route("Review Screen")

    @Serializable
    data object ProfileScreen : Route("Profile")

    @Serializable
    data object LoginRegisterScreen : Route("Login / Register")

    @Serializable
    data object LoginScreen : Route("Login")

    @Serializable
    data object RegisterScreen : Route("Register")
}
