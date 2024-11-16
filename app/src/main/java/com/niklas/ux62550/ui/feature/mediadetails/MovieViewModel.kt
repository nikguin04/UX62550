package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.minutes

class MovieViewModel : ViewModel() {
    private val movie = Movie(
        name = "RED: The Movie",
        year = "2022",
        duration = 131.minutes,
        rating = 3.5,
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
        genres = listOf("Action", "Dinosaur Adventure", "Romance"),
        pgRating = 18,
        tempColor = Color.Red
    )

    private val similarMedia = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Green),
    )

    private val mutableMovieState = MutableStateFlow<Movie>(movie)
    val movieState: StateFlow<Movie> = mutableMovieState

    private val mutableSimilarMediaState = MutableStateFlow<List<MediaItem>>(similarMedia)
    val similarMediaState: StateFlow<List<MediaItem>> = mutableSimilarMediaState
}
