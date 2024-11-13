package com.niklas.ux62550.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val title: String) {
    @Serializable
    data object HomeScreen : Route("Home Screen")

    @Serializable
    data class MediaDetailsScreen(val name: String) : Route("Media Details Screen")
}
