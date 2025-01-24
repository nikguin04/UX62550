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
    // https://firebase.google.com/docs/auth/android/start
    // With the code exsample
    // https://github.com/firebase/snippets-android/blob/f8eb9475c84dd5e76a1895773ede2ec1ef8553f9/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin/EmailPasswordActivity.kt#L15-L15
    // https://developer.android.com/training/basics/intents/result
    private lateinit var auth: FirebaseAuth


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    // https://firebase.google.com/docs/auth/android/start
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

    // https://firebase.google.com/docs/auth/android/start
     fun createAccount(email: String, password: String, name: String, onSuccess: () -> Unit = {}, onError: () -> Unit = {}){
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
                addUsername(name)
            }
            .addOnFailureListener{
                onError()
            }
    }
    private fun addUsername(nameID: String){
        val userName = mapOf("Name" to nameID)
        FirebaseInstance.getDB()!!.collection("UserData").document(getAuth().currentUser?.uid.toString()).set(userName)
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