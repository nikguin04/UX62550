package com.niklas.ux62550.data.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.niklas.ux62550.data.model.MediaObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.tasks.await

val db = Firebase.firestore

class RemoteFirebase {
    suspend fun getWatchList(mutableWatchListFlow: MutableSharedFlow<List<Int>?>){

        try {
            val document = db.collection("Watchlist").document("1NhBN640YoUdZq848o3C").get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            val arrayData = document.data?.get("MovieIds") as List<*>
            val intData = arrayData.mapNotNull { (it as? Long)?.toInt() } // Filters out everything that is not a long, and converts it to Int (movie_id is int32 according to TMDB)
            mutableWatchListFlow.emit(intData)
        } catch (e: Exception){
            Log.w("Firebase_info", "Error getting documents.", e)
            mutableWatchListFlow.emit(null)
        }
    }
}



