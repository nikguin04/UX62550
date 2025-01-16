package com.niklas.ux62550.ui.feature.profile



import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.niklas.ux62550.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ProfileViewModel : ViewModel() {
    private val profile = Profile(
        name = "Simone",
        Email = "*****@gmail.com",
        FacvritMovieID = 1,
        tempColor = Color.Red

    )

    private val mutableProfileState = MutableStateFlow<Profile>(profile)
    val profileState: StateFlow<Profile> = mutableProfileState



    // From some of the documenttaion from firebase

}
