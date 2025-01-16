package com.niklas.ux62550.ui.feature.profile

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseAuthController: Activity() {

    lateinit var auth: FirebaseAuth
    var isFirstTime = true


    fun gettheauth(){
        if(isFirstTime == true){
            auth = Firebase.auth
            isFirstTime = false
        }
    }
    fun onCreate(email: String, password: String) {
        gettheauth()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }


    fun signIn(email: String, password: String){
        gettheauth()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                }
            }



    }


}