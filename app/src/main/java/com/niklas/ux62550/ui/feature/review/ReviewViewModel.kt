package com.niklas.ux62550.ui.feature.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ReviewStateDataClass(
    val rating: Float = 0f,
    val reviewText: String = "",
    val categoryRatings: Map<String, Float> =
        ReviewViewModel.ReviewCategoryList.map { it to 0f }.toMap()
)

class ReviewViewModel : ViewModel() {
    companion object {
        val ReviewCategoryList = listOf("Music", "Plot", "Acting", "Directing")
    }

    private val reviewRepository = DataModule.reviewRepository

    private val reviewStateFlow = MutableStateFlow(ReviewStateDataClass())
    val reviewState: StateFlow<ReviewStateDataClass> = reviewStateFlow

    // Update the overall rating
    fun updateRating(rating: Float) {
        reviewStateFlow.value = reviewStateFlow.value.copy(rating = rating)
    }

    fun submitReview(mediaId: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        val review = mapOf(
            "MovieIDs" to mediaId,
            "MainRating" to reviewStateFlow.value.rating,
            "ReviewText" to reviewStateFlow.value.reviewText,
            "CategoryRatings" to reviewStateFlow.value.categoryRatings,
            "timestamp" to System.currentTimeMillis()
        )

        viewModelScope.launch {
            reviewRepository.addReivewToFirebase(review = review, onSuccess = onSuccess, onError = onError)
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
