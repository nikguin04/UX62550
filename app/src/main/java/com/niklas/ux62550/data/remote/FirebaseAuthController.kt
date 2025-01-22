package com.niklas.ux62550.data.remote

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseAuthController: Activity() {
    // taken from firebase documentation

    private lateinit var auth: FirebaseAuth


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }


     fun signIn(email: String, password: String , onSuccess: () -> Unit = {}, onError: () -> Unit = {}){
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                onError()
            }
    }

     fun createAccount(email: String, password: String, onSuccess: () -> Unit = {}, onError: () -> Unit = {}){
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                onError()
            }
    }

    public fun getAuth() : FirebaseAuth{
        auth = Firebase.auth
        return auth
    }


    public fun logout() {
        auth = Firebase.auth
        return auth.signOut()
    }
}


sealed class BoolFetchStatus {
    data object Working : BoolFetchStatus()
    data class Result(val result: Boolean) : BoolFetchStatus()
}