package com.niklas.ux62550.ui.feature.profile

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

    private val mutableProfileState = MutableStateFlow<UserData>(UserData.Empty)
    val profileState: StateFlow<UserData> = mutableProfileState

    init {
        viewModelScope.launch {
            profileRepository.userFlow.collect { user ->
                mutableProfileState.update {
                    val profile = Profile(
                        name = user[0],
                        email = user[1]
                    )
                    UserData.Data(profile)
                }
            }
        }
        getUserName()
    }

    private fun getUserName() = viewModelScope.launch {
        profileRepository.getUserData()
    }
}

sealed class UserData {
    data object Empty : UserData()
    data class Data(val userData: Profile) : UserData()
    data object Error : UserData()
}
