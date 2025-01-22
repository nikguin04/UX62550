package com.niklas.ux62550.ui.feature.profile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.niklas.ux62550.data.remote.FirebaseAuthController
import com.niklas.ux62550.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val profile = Profile(
        name = "Simone",
        Email = "*****@gmail.com",
        FacvritMovieID = 1,
        tempColor = Color.Red
    )

    private val mutableProfileState = MutableStateFlow<Profile>(profile)
    val profileState: StateFlow<Profile> = mutableProfileState



    fun sigin(email: String, password: String){
        viewModelScope.launch { FirebaseAuthController().signIn(email.toString(), password.toString())}
    }
}