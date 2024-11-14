package com.niklas.ux62550.navigation

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

    @Serializable
    data object  LoginRegisterScreen : Route("Login / Register")
    @Serializable
    data object  LoginScreen : Route("Login")
    @Serializable
    data object  RegisterScreen : Route("Register")
    @Serializable
    data object  ProfileScreen : Route("Profile")
}