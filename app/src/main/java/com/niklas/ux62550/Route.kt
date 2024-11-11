package com.niklas.ux62550

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val title: String) {
    /*@Serializable
    data class BlueScreen(val name: String) : Route("Blue Screen")

    @Serializable
    data object GreenScreen : Route("Green Screen")*/

    @Serializable
    data object HomeScreen : Route("Home Screen")

    @Serializable
    data class MediaDetailsScreen(val md_name: String) : Route("Media Details Screen")
}