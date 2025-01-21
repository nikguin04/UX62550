package com.niklas.ux62550.ui.feature.profile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.niklas.ux62550.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ProfileViewModel : ViewModel() {
    private val profile = Profile(
        name = "Simone",
        Email = "simonrolsen@gmail.com",
        FacvritMovieID = 1,
        tempColor = Color.Red

    )

    private val mutableProfileState = MutableStateFlow<Profile>(profile)
    val profileState: StateFlow<Profile> = mutableProfileState
}