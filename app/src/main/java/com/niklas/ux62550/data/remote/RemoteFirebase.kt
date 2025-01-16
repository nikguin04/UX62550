package com.niklas.ux62550.data.remote

import android.provider.MediaStore.Video.Media
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
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
    suspend fun getReview(movieId: Int){
        val detailedRating = mutableMapOf<Int, Int>()

        try {
            val document = FirebaseInstance.getDB()!!.collection("UserReviews").document("Movies").collection(movieId.toString()).get().await()
            for (rating in document.documents){
                val ratings = document.get("CategoryRatings") as? List<String>
                val actorRating
                val directingRating
                val musicRating
                val plotRating

                if(rating != null){
                    Log.d("Firebase_info", "$actorRating => $actorRating")
                    Log.d("Firebase_info", "$directingRating => $directingRating")
                    Log.d("Firebase_info", "$musicRating => $musicRating")
                    Log.d("Firebase_info", "$plotRating => $plotRating")
                }
            }
        } catch (e: Exception)
        {
            Log.w("Firebase_info", "Error getting documents.", e)
        }
        return
    }

    suspend fun UpdateToWatchList(data: MediaObject, remove: Boolean){
        val watchlist = FirebaseInstance.getDB()!!.collection("Watchlist").document("1NhBN640YoUdZq848o3C")
        // Set the the movieID
        // Atomically add or remove a new region to the "MovieIds" array field.
        watchlist.update(
            "MovieIds",
            if (remove) {FieldValue.arrayRemove(data.id)}
            else {FieldValue.arrayUnion(data.id)}
        )
    }
}



