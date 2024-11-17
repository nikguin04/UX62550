package com.niklas.ux62550.models

import androidx.compose.ui.graphics.Color
import kotlin.time.Duration

data class Movie(
    val name: String,
    val year: String,
    val duration: Duration,
    val rating: Double,
    val description: String,
    //val image: Image,
    //val trailer: Image,
    val genres: List<String>,
    val pgRating: Int,
    //val actorsAndDirectors: List<String>,
    //val award: String
    //val whereToWatch: List<Image>
    val tempColor: Color
)
