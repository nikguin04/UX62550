package com.niklas.ux62550.ui.feature.mediadetails



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
    companion object {
        lateinit var auth: FirebaseAuth
        var isFirstTime = true


        fun gettheauth(){
            if(isFirstTime == true){
                auth = Firebase.auth
                isFirstTime = false
            }
        }
        fun onCreate(Email: String, Password: String) {
            gettheauth()
            if(auth.createUserWithEmailAndPassword(Email, Password).isSuccessful){
                Log.w("login", "No problem user log in.")
            } else{
                Log.w("login", "nope fuck the user.")
            }
        }


        fun signIn(Email: String, Password: String){
            gettheauth()
                if (auth.signInWithEmailAndPassword(Email, Password).result.user != null) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w("login", "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "signInWithEmail:failure")
                }


        }


    }


}
