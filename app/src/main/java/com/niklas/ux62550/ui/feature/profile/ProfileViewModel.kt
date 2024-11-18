package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.minutes

class ProfileViewModel : ViewModel() {
    private val profile = Profile(
        name = "Simone",
        Email = "*****@gmail.com",
        FacvritMovieID = 1,
        tempColor = Color.Red

    )

    private val mutableProfileState = MutableStateFlow<Profile>(profile)
    val profileState: StateFlow<Profile> = mutableProfileState

}
