import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ReviewState(
    val rating: Float = 0f,
    val reviewText: String = "",
    val categoryRatings: Map<String, Float> = mapOf(
        "Music" to 0f,
        "Plot" to 0f,
        "Acting" to 0f,
        "Directing" to 0f
    )
)

class ReviewViewModel : ViewModel() {
    private val _reviewState = MutableStateFlow(ReviewState())
    val reviewState: StateFlow<ReviewState> = _reviewState

    // Update the overall rating
    fun updateRating(rating: Float) {
        _reviewState.value = _reviewState.value.copy(rating = rating)
    }

    // Update the review text
    fun updateReviewText(text: String) {
        _reviewState.value = _reviewState.value.copy(reviewText = text)
    }

    // Get the category rating
    fun getCategoryRating(category: String): Float {
        return _reviewState.value.categoryRatings[category] ?: 0f
    }

    // Update a specific category rating
    fun updateCategoryRating(category: String, rating: Float) {
        val updatedRatings = _reviewState.value.categoryRatings.toMutableMap()
        updatedRatings[category] = rating
        _reviewState.value = _reviewState.value.copy(categoryRatings = updatedRatings)
    }

    // Submit the review to Firebase
    fun submitReview(mediaId: Int) {
        val review = mapOf(
            "MovieIDs" to mediaId,
            "MovieRatings" to _reviewState.value.rating,
            "ReviewText" to _reviewState.value.reviewText,
            "CategoryRatings" to _reviewState.value.categoryRatings,
            "timestamp" to System.currentTimeMillis()
        )

        FirebaseFirestore.getInstance().collection("Review")
            .add(review)
            .addOnSuccessListener { println("Review submitted successfully!") }
            .addOnFailureListener { println("Error submitting review: ${it.message}") }
    }
}
