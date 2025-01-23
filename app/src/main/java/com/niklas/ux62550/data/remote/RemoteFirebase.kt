package com.niklas.ux62550.data.remote

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.niklas.ux62550.data.model.MediaObject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import java.math.RoundingMode

// We need to make this an absolute singleton and not an object, since a static reference to a firestore database causes a memory leak
class FirebaseInstance {
    private val db = Firebase.firestore

    companion object {
        private var instance: FirebaseInstance? = null
        fun getDB(): FirebaseFirestore? {
            if (instance == null) {
                instance = FirebaseInstance()
            }
            return instance?.db
        }
    }
}

object RemoteFirebase {
    suspend fun getWatchList(mutableWatchListFlow: MutableSharedFlow<List<Int>?>) {
        try {
            // if true then use the userId will be used else it will use the default user
            // in the real app there will be no default user you need to sign in to used this function
            var UserIdPath = "1NhBN640YoUdZq848o3C"
            if (FirebaseAuthController().getAuth().currentUser != null) {
                UserIdPath = FirebaseAuthController().getAuth().uid.toString()
            }

            var document = FirebaseInstance.getDB()!!.collection("Watchlist").document(UserIdPath).get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            val arrayData = document.data?.get("MovieIds") as List<*>
            val intData = arrayData.mapNotNull { (it as? Long)?.toInt() } // Filters out everything that is not a long, and converts it to Int (movie_id is int32 according to TMDB)
            mutableWatchListFlow.emit(intData)
        } catch (e: Exception) {
            Log.w("Firebase_info", "Error getting documents.", e)
            mutableWatchListFlow.emit(null)
        }
    }

    // Help from chat
    suspend fun getReview(movieId: Int): Map<String, Double> {
        var totalMainRating = 0.0
        var totalActorRating = 0.0
        var totalDirectingRating = 0.0
        var totalMusicRating = 0.0
        var totalPlotRating = 0.0
        var reviewCount = 0.0

        try {
            val document = FirebaseInstance.getDB()!!.collection("UserReviews").document("Movies").collection(movieId.toString()).get().await()
            if (document != null && document.isEmpty.not()) {
                for (documents in document.documents) {
                    val mainRating = documents.get("MainRating") as Double
                    val ratings = documents.get("CategoryRatings") as Map<String, Double>

                    totalMainRating += mainRating
                    totalActorRating += ratings["Acting"] ?: 0.0
                    totalDirectingRating += ratings["Directing"] ?: 0.0
                    totalMusicRating += ratings["Music"] ?: 0.0
                    totalPlotRating += ratings["Plot"] ?: 0.0
                    reviewCount++
                }
            } else {
                Log.d("Firebase_info", "No reviews found for movieId: $movieId")
            }
        } catch (e: Exception) {
            Log.w("Firebase_info", "Error getting documents for movieId: $movieId", e)
        }

        val averageMainRating = if (reviewCount > 0) totalMainRating / reviewCount else 0.0
        val averageActorRating = if (reviewCount > 0) totalActorRating / reviewCount else 0.0
        val averageDirectingRating = if (reviewCount > 0) totalDirectingRating / reviewCount else 0.0
        val averageMusicRating = if (reviewCount > 0) totalMusicRating / reviewCount else 0.0
        val averagePlotRating = if (reviewCount > 0) totalPlotRating / reviewCount else 0.0

        return mapOf(
            "MainRating" to BigDecimal(averageMainRating).setScale(1, RoundingMode.DOWN).toDouble(),
            "Acting" to BigDecimal(averageActorRating).setScale(1, RoundingMode.DOWN).toDouble(),
            "Directing" to BigDecimal(averageDirectingRating).setScale(1, RoundingMode.DOWN).toDouble(),
            "Music" to BigDecimal(averageMusicRating).setScale(1, RoundingMode.DOWN).toDouble(),
            "Plot" to BigDecimal(averagePlotRating).setScale(1, RoundingMode.DOWN).toDouble()
        )
    }

    suspend fun UpdateToWatchList(data: MediaObject, remove: Boolean) {
        val Watchlistlist = mapOf(
            "MovieIds" to listOf(data.id)
        )
        // if true then use the userId will be used else it will use the default user
        // in the real app there will be no default user you need to sign in to used this function
        var UserIdPath = "1NhBN640YoUdZq848o3C"
        if (FirebaseAuthController().getAuth().currentUser != null) {
            UserIdPath = FirebaseAuthController().getAuth().uid.toString()
        }

        val document = FirebaseFirestore.getInstance().collection("Watchlist").document(UserIdPath)

        if (document.get().await().data != null) {
            document.update(
                "MovieIds",
                if (remove) {
                    FieldValue.arrayRemove(data.id)
                } else {
                    FieldValue.arrayUnion(data.id)
                }
            )
        } else {
            document.set(Watchlistlist)
        }
    }

    suspend fun getUserData(mutableUserFlow: MutableSharedFlow<List<String>>) {
        try {
            var userIdPath = "1NhBN640YoUdZq848o3C"
            if (FirebaseAuthController().getAuth().currentUser != null) {
                userIdPath = FirebaseAuthController().getAuth().uid.toString()
            }

            val document = FirebaseInstance.getDB()!!
                .collection("UserData").document(userIdPath).get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            val userName = document.data?.get("Name") as? String ?: "Default Name"
            val emailAddress = FirebaseAuthController().getAuth().currentUser?.email ?: "default@gmail.com"

            mutableUserFlow.emit(listOf(userName, emailAddress))
        } catch (e: Exception) {
            Log.w("Firebase_info", "Error getting documents.", e)
        }
    }
}
