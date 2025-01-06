package com.niklas.ux62550.navigation

import com.niklas.ux62550.data.model.MediaObject
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
    data class MediaDetailsScreen(val media: MediaObject) : Route("Media Details Screen")

    @Serializable
    data class ReviewScreen(val name: String) : Route("Review Screen")

    @Serializable
    data object ProfileScreen : Route("Profile")

    @Serializable
    data object LoginRegisterScreen : Route("Login / Register")

    @Serializable
    data object LoginScreen : Route("Login")

    @Serializable
    data object RegisterScreen : Route("Register")
}
