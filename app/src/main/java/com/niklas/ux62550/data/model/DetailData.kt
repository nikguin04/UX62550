package com.niklas.ux62550.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieDetailObject(
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,

    @SerialName("genres")
    val genres: List<Genre>,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("homepage")
    val homePage: String = "",

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("overview")
    val description: String = "",

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("runtime")
    val runTime: Int = 0,

    @SerialName("id")
    val id: Int,
) : Parcelable {
    fun toMediaObject(): MediaObject {
        return MediaObject(
            adult = adult,
            backdropPath = backdropPath,
            id = id,
            title = title,
            posterPath = posterPath,
            genreIds = genres.map { it.id }, // TODO: check this mapping is right
            releaseDate = releaseDate,
            mediaType = "movie", // This is hardcoded because the MovieDetailObject is called "movie", for fetching tv shows this should be changed
            voteAverage = voteAverage.toFloat()
        )
    }
}

@Parcelize
@Serializable
data class Genre(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = ""
) : Parcelable

@Parcelize
@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val name: String = "",

    @SerialName("iso_639_1")
    val code: String = "",

    @SerialName("name")
    val nativeName: String = ""
) : Parcelable
