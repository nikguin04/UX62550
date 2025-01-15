package com.niklas.ux62550.data.remote

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.niklas.ux62550.data.model.MediaObject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.tasks.await

class FirebaseInstance { // We need to make this an absolute singleton and not an object, since a static reference to a firestore database causes a memory leak
    private val db = Firebase.firestore
    companion object {
        private var instance: FirebaseInstance? = null
        fun getDB(): FirebaseFirestore? {
            if (instance == null) { instance = FirebaseInstance() }
            return instance.let { fb -> fb?.db }
        }
    }
}

object RemoteFirebase {

    suspend fun getWatchList(mutableWatchListFlow: MutableSharedFlow<List<Int>?>){

        try {
            val document = FirebaseInstance.getDB()!!.collection("Watchlist").document("1NhBN640YoUdZq848o3C").get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            val arrayData = document.data?.get("MovieIds") as List<*>
            val intData = arrayData.mapNotNull { (it as? Long)?.toInt() } // Filters out everything that is not a long, and converts it to Int (movie_id is int32 according to TMDB)
            mutableWatchListFlow.emit(intData)
        } catch (e: Exception){
            Log.w("Firebase_info", "Error getting documents.", e)
            mutableWatchListFlow.emit(null)
        }
    }
    suspend fun addToWatchList(data: MediaObject){
        try {
            db.collection("Watchlist").document("1NhBN640YoUdZq848o3C").set(data.id)
        } catch (e: Exception){
            Log.w("Firebase_info", "No movieID found", e)
        }
    }
}



