package com.niklas.ux62550.data.remote

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseAuthController : Activity() {
    // taken from firebase documentation

    private lateinit var auth: FirebaseAuth

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError() }
    }

    fun createAccount(email: String, password: String, name: String, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess(); addUsername(name) }
            .addOnFailureListener { onError() }
    }

    private fun addUsername(nameID: String) {
        val userName = mapOf("Name" to nameID)
        FirebaseInstance.getDB()!!.collection("UserData").document(getAuth().currentUser?.uid.toString()).set(userName)
    }

    public fun getAuth(): FirebaseAuth {
        auth = Firebase.auth
        return auth
    }

    public fun logout() {
        auth = Firebase.auth
        return auth.signOut()
    }
}
