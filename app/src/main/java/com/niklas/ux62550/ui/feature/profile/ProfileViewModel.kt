package com.niklas.ux62550.ui.feature.profile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.ProfileRepository
import com.niklas.ux62550.models.Profile
import com.niklas.ux62550.ui.feature.watchlist.MovieIds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val profileRepository = DataModule.profileRepository

    private val profile = Profile(
        name = "Simone",
        Email = "simonrolsen@gmail.com",
        FacvritMovieID = 1,
        tempColor = Color.Red

    )

    private fun getUserName() = viewModelScope.launch {
        profileRepository.getUserData()
    }
    init {
        viewModelScope.launch {
            profileRepository.userFlow.collect { UserName ->
                mutableProfileState.update {
                    UserName
                }
            }
        }
        getUserName()
    }

    private val mutableProfileState = MutableStateFlow<Profile>(profile)
    val profileState: StateFlow<Profile> = mutableProfileState
}
sealed class UserName {
    data object Empty : UserName()
    data class Data(val username: String) : UserName()
    data object Error : UserName()
}