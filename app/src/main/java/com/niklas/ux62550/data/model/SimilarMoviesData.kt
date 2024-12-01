package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SimilarMoviesObject(
    @SerialName("results")
    val resultsSimilarMovies : List<SimilarMoviesPic>,
)

@Serializable
data class SimilarMoviesPic(
    @SerialName("backdrop_path")
    val backDropPath: String? = null,

    @SerialName("original_title")
    val title: String
    )

