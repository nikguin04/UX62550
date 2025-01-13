package com.niklas.ux62550.ui.feature.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.domain.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

        private val reviewRepository = ReviewRepository()

        private fun getReview() = viewModelScope.launch {
            reviewRepository.getReview()
        }

        private val mutableReviewState = MutableStateFlow<MovieIds>(MovieIds.Empty)
        val reviewState: StateFlow<MovieIds> = mutableReviewState


        init {
            viewModelScope.launch {
                reviewRepository.reviewFlow.collect { movieIdArray ->
                    mutableReviewState.update {
                        MovieIds.Data(movieIdArray)
                    }
                }
            }
            getReview()
        }
    }

sealed class MovieIds {
    data object Empty : MovieIds()
    data class Data(val movies: List<Int>?) : MovieIds()
}