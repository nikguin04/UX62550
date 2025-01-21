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
            // if true then use the  defult user will be used else it will use the user that is signed in
            // in the real app there will be no defult user you need to sign in to used this function
            if(FirebaseAuthController().getAuth().currentUser == null){
                var document = FirebaseInstance.getDB()!!.collection("Watchlist").document("1NhBN640YoUdZq848o3C").get().await()
                Log.d("Firebase_info", "${document.id} => ${document.data}")
                val arrayData = document.data?.get("MovieIds") as List<*>
                val intData = arrayData.mapNotNull { (it as? Long)?.toInt() } // Filters out everything that is not a long, and converts it to Int (movie_id is int32 according to TMDB)
                mutableWatchListFlow.emit(intData)
                return
            }

            var document = FirebaseInstance.getDB()!!.collection("Watchlist").document(FirebaseAuthController().getAuth().uid.toString()).get().await()
            Log.d("Firebase_info", "${document.id} => ${document.data}")
            val arrayData = document.data?.get("MovieIds") as List<*>
            val intData = arrayData.mapNotNull { (it as? Long)?.toInt() } // Filters out everything that is not a long, and converts it to Int (movie_id is int32 according to TMDB)
            mutableWatchListFlow.emit(intData)

        } catch (e: Exception){
            Log.w("Firebase_info", "Error getting documents.", e)
            mutableWatchListFlow.emit(null)
        }
    }
    //Help from chat
    suspend fun getReview(movieId: Int): Map<String, Double> {
        var totalMainRating = 0
        var totalActorRating = 0
        var totalDirectingRating = 0
        var totalMusicRating = 0
        var totalPlotRating = 0
        var reviewCount = 0

        try {
            val document = FirebaseInstance.getDB()!!.collection("UserReviews").document("Movies").collection(movieId.toString()).get().await()
            if (document != null && document.isEmpty.not()) {
                for (documents in document.documents) {
                    val mainRating = documents.get("MainRating") as Double
                    val ratings = documents.get("CategoryRatings") as Map<String, Double>


                    totalMainRating += mainRating.toInt()
                    totalActorRating += (ratings["Acting"] ?: 0).toInt()
                    totalDirectingRating += (ratings["Directing"] ?: 0).toInt()
                    totalMusicRating += (ratings["Music"] ?: 0).toInt()
                    totalPlotRating += (ratings["Plot"] ?: 0).toInt()
                    reviewCount++
                }
            } else {
                Log.d("Firebase_info", "No reviews found for movieId: $movieId")
            }
        } catch (e: Exception) {
            Log.w("Firebase_info", "Error getting documents for movieId: $movieId", e)
        }

        val averageMainRating = if (reviewCount > 0) totalMainRating.toDouble() / reviewCount else 0.0
        val averageActorRating = if (reviewCount > 0) totalActorRating.toDouble() / reviewCount else 0.0
        val averageDirectingRating = if (reviewCount > 0) totalDirectingRating.toDouble() / reviewCount else 0.0
        val averageMusicRating = if (reviewCount > 0) totalMusicRating.toDouble() / reviewCount else 0.0
        val averagePlotRating = if (reviewCount > 0) totalPlotRating.toDouble() / reviewCount else 0.0

        return mapOf(
            "MainRating" to averageMainRating,
            "Acting" to averageActorRating,
            "Directing" to averageDirectingRating,
            "Music" to averageMusicRating,
            "Plot" to averagePlotRating
        )
    }

    suspend fun UpdateToWatchList(data: MediaObject, remove: Boolean){
        val Watchlistlist = mapOf(
            "MovieIds" to listOf(data.id)
        )

        // if true then use the user that is sigent ind else the defult user will be used
        // in the real app there will be no defult user you need to sign in to used this function
        if(FirebaseAuthController().getAuth().currentUser != null){
            val document = FirebaseFirestore.getInstance().collection("Watchlist").document(FirebaseAuthController().getAuth().uid.toString())

            if(document.get().await().data != null){
                document.update(
                    "MovieIds",
                    if (remove) {FieldValue.arrayRemove(data.id)}
                    else {FieldValue.arrayUnion(data.id)}
                )
            } else{
                document.set(Watchlistlist)
            }
            return
        }

        val watchlist = FirebaseInstance.getDB()!!.collection("Watchlist").document("1NhBN640YoUdZq848o3C")
        watchlist.update(
            "MovieIds",
            if (remove) {FieldValue.arrayRemove(data.id)}
            else {FieldValue.arrayUnion(data.id)}
        )
    }

    fun addReivewToFirebase(review: Map<String, Any>){
        // shoud this be move to the firebase repository
        // if true then use the user that is sigent ind else the defult user will be used
        // in the real app there will be no defult user you need to sign in to used this function
        if(FirebaseAuthController().getAuth().currentUser?.uid != null) {
            val document = FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(FirebaseAuthController().getAuth().currentUser?.uid.toString())
            if (document.get().isSuccessful) {
                document.update(review)
            } else {
                document
                    .set(review)
                    .addOnSuccessListener { println("Review submitted successfully!") }
                    .addOnFailureListener { println("Error submitting review: ${it.message}") }
            }
            return
        }

        val document = FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document("User1")
        if(document.get().isSuccessful){
            document.update(review)
        } else {
            document
                .set(review)
                .addOnSuccessListener { println("Review submitted successfully!") }
                .addOnFailureListener { println("Error submitting review: ${it.message}") }
        }
    }
}



