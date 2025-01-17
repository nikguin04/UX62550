package com.niklas.ux62550.data.remote

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
    suspend fun getReview(movieId: Int): Map<String, Double> {
        var totalActorRating = 0
        var totalDirectingRating = 0
        var totalMusicRating = 0
        var totalPlotRating = 0
        var reviewCount = 0

        try {
            val document = FirebaseInstance.getDB()!!.collection("UserReviews").document("Movies").collection(movieId.toString()).get().await()
            if (document != null && document.isEmpty.not()) {
                for (documents in document.documents) {
                    val ratings = documents.get("CategoryRatings") as Map<String, Long>

                    totalActorRating += (ratings["actor"] ?: 0).toInt()
                    totalDirectingRating += (ratings["directing"] ?: 0).toInt()
                    totalMusicRating += (ratings["music"] ?: 0).toInt()
                    totalPlotRating += (ratings["plot"] ?: 0).toInt()
                    reviewCount++
                }
            } else {
                Log.d("Firebase_info", "No reviews found for movieId: $movieId")
            }
        } catch (e: Exception) {
            Log.w("Firebase_info", "Error getting documents for movieId: $movieId", e)
        }

        val averageActorRating = if (reviewCount > 0) totalActorRating.toDouble() / reviewCount else 0.0
        val averageDirectingRating = if (reviewCount > 0) totalDirectingRating.toDouble() / reviewCount else 0.0
        val averageMusicRating = if (reviewCount > 0) totalMusicRating.toDouble() / reviewCount else 0.0
        val averagePlotRating = if (reviewCount > 0) totalPlotRating.toDouble() / reviewCount else 0.0

        return mapOf(
            "actor" to averageActorRating,
            "directing" to averageDirectingRating,
            "music" to averageMusicRating,
            "plot" to averagePlotRating
        )
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



