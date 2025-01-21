package com.niklas.ux62550.domain

import com.google.firebase.firestore.FirebaseFirestore
import com.niklas.ux62550.data.remote.FirebaseAuthController
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ReviewRepository(
    private val firebaseDataSource: RemoteFirebase
) {

    private val mutableReviewFlow = MutableSharedFlow<List<Int>?>()
    val reviewFlow = mutableReviewFlow.asSharedFlow()

    private val mutableReviewResult = MutableSharedFlow<Boolean>(replay = 1)
    val reviewResult = mutableReviewResult.asSharedFlow()


    fun addReivewToFirebase(review: Map<String, Any>){
        // if true then use the userId will be used else it will use the defult user
        // in the real app there will be no defult user you need to sign in to used this function
        var UserIdPath = "1NhBN640YoUdZq848o3C"
        if(FirebaseAuthController().getAuth().currentUser != null){
            UserIdPath = FirebaseAuthController().getAuth().uid.toString()
        }

        val document = FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(UserIdPath)
        if(document.get().isSuccessful){
            document.update(review)
                .addOnSuccessListener { mutableReviewResult.tryEmit(true) }
                .addOnFailureListener { mutableReviewResult.tryEmit(false) }
        } else {
            document
                .set(review)
                .addOnSuccessListener { mutableReviewResult.tryEmit(true) }
                .addOnFailureListener { mutableReviewResult.tryEmit(false) }
        }
    }
}