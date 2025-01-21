package com.niklas.ux62550.ui.feature.profile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val profileRepository = DataModule.profileRepository

    private val mutableProfileState = MutableStateFlow<UserName>(UserName.Empty)
    val profileState: StateFlow<UserName> = mutableProfileState

    init {
        viewModelScope.launch {
            profileRepository.userFlow.collect { userName ->
                mutableProfileState.update {
                    val profile = Profile(
                        name = userName,
                        Email = "default@gmail.com",
                        FavoriteMovieID = 1,
                        tempColor = Color.Gray
                    )
                    UserName.Data(profile)
                }
            }
        }
        getUserName()
    }
    private fun getUserName() = viewModelScope.launch {
        profileRepository.getUserData()
    }
}


sealed class UserName {
    data object Empty : UserName()
    data class Data(val userData: Profile) : UserName()
    data object Error : UserName()
}