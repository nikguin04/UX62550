package com.niklas.ux62550.data.remote

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.niklas.ux62550.data.model.MediaObject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.tasks.await

val db = Firebase.firestore

class RemoteFirebase {
    suspend fun getWatchList(mutableWatchListFlow: MutableSharedFlow<List<Int>?>){

        try {
            val document = db.collection("Watchlist").document("1NhBN640YoUdZq848o3C").get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            mutableWatchListFlow.emit(document.data?.get("MovieIds") as List<Int> ) // TODO make type safe.
        } catch (e: Exception){
            Log.w("Firebase_info", "Error getting documents.", e)
            mutableWatchListFlow.emit(null)
        }
    }
    fun addToWatchList(data: MediaObject){
        try {
            db.collection("Watchlist").document("1NhBN640YoUdZq848o3C").set(data.id)
        } catch (e: Exception){
            Log.w("Firebase_info", "No movieID found", e)
        }
    }
}



