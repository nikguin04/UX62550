package com.niklas.ux62550.domain

import com.google.firebase.firestore.FirebaseFirestore
import com.niklas.ux62550.data.remote.FirebaseAuthController

class ReviewRepository {
    fun addReviewToFirebase(review: Map<String, Any>, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
        // if true then use the userId will be used else it will use the default user
        // in the real app there will be no default user you need to sign in to used this function
        var userId = "1NhBN640YoUdZq848o3C"
        if (FirebaseAuthController().getAuth().currentUser != null) {
            userId = FirebaseAuthController().getAuth().uid.toString()
        }

        val document = FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(userId)
        if (document.get().isSuccessful) {
            document.update(review)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        } else {
            document
                .set(review)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError() }
        }
    }
}
