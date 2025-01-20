import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.niklas.ux62550.data.remote.FirebaseAuthController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



data class ReviewStateDataClass(
    val rating: Float = 0f,
    val reviewText: String = "",
    val categoryRatings: Map<String, Float> =
        ReviewViewModel.ReviewCategoryList.map { it to 0f}.toMap()
)

class ReviewViewModel : ViewModel() {
    companion object {
        val ReviewCategoryList = listOf("Music", "Plot", "Acting", "Directing")
    }

    private val reviewStateFlow = MutableStateFlow(ReviewStateDataClass())
    val reviewState: StateFlow<ReviewStateDataClass> = reviewStateFlow

    // Update the overall rating
    fun updateRating(rating: Float) {
        reviewStateFlow.value = reviewStateFlow.value.copy(rating = rating)
    }

    fun submitReview(mediaId: Int) {
        val review = mapOf(
            "MovieIDs" to mediaId,
            "MainRating" to reviewStateFlow.value.rating,
            "ReviewText" to reviewStateFlow.value.reviewText,
            "CategoryRatings" to reviewStateFlow.value.categoryRatings,
            "timestamp" to System.currentTimeMillis()
        )

        // shoud this be move to the firebase repository
        // if true then use the user that is sigent ind else the defult user will be used
        // in the real app there will be no defult user you need to sign in to used this function
        if(FirebaseAuthController().getAuth().currentUser?.uid != null){
            if(FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(FirebaseAuthController().getAuth().currentUser?.uid.toString()).get().isSuccessful){
                FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(FirebaseAuthController().getAuth().currentUser?.uid.toString()).update(review)
            } else {
                FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document(FirebaseAuthController().getAuth().currentUser?.uid.toString())
                    .set(review)
                    .addOnSuccessListener { println("Review submitted successfully!") }
                    .addOnFailureListener { println("Error submitting review: ${it.message}") }

            }
        } else {
            if(FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document("User1").get().isSuccessful){
                FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document("User1").update(review)
            } else {
                FirebaseFirestore.getInstance().collection("UserReviews").document("Movies").collection(review.getValue("MovieIDs").toString()).document("User1")
                    .set(review)
                    .addOnSuccessListener { println("Review submitted successfully!") }
                    .addOnFailureListener { println("Error submitting review: ${it.message}") }
            }
        }
    }

    // Update the review text
    fun updateReviewText(text: String) {
        reviewStateFlow.value = reviewStateFlow.value.copy(reviewText = text)
    }

    // Get the category rating
    fun getCategoryRating(category: String): Float {
        return reviewStateFlow.value.categoryRatings[category] ?: 0f
    }


    // Update a specific category rating
    fun updateCategoryRating(category: String, rating: Float) {
        val updatedRatings = reviewStateFlow.value.categoryRatings.toMutableMap()
        updatedRatings[category] = rating // Update the rating for the selected category
        reviewStateFlow.value = reviewStateFlow.value.copy(categoryRatings = updatedRatings) // Update the state
    }
}
