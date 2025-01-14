package com.niklas.ux62550.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovieDetailObject(
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguages>?,

    @SerialName("genres")
    val genre: List<Genre>,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backDropPath : String?,

    @SerialName("homepage")
    val homePage : String = "",

    @SerialName("original_title")
    val Originaltitle : String,

    @SerialName("overview")
    val Description : String = "",

    @SerialName("poster_path")
    val posterPath : String?,

    @SerialName("release_date")
    val relaseDate : String = "0",

    @SerialName("title")
    val title: String = "",

    @SerialName("vote_average")
    val rating : Double = 0.0,

    @SerialName("runtime")
    val runTime : Int = 0,

    @SerialName("id")
    val id : Int,

): Parcelable {
    fun toMediaObject(): MediaObject {
        return MediaObject(
            adult = this.adult,
            backdrop_path = this.backDropPath,
            id = this.id,
            title = this.title,
            poster_path = this.posterPath,
            genre_ids = this.genre.map { it.genreID }, // TODO: check this mapping is right
            release_date = this.relaseDate,
            media_type = "movie" // This is hardcoded because the MovieDetailObject is called "movie", for fetching tv shows this should be changed
        )
    }
}
@Parcelize
@Serializable
data class Genre(
    @SerialName("id")
    val genreID: Int = 0,

    @SerialName("name")
    val genreName: String = ""
): Parcelable

@Parcelize
@Serializable
data class SpokenLanguages(
    @SerialName("english_name")
    val languageName : String = "",

    @SerialName("iso_639_1")
    val isoNumber: String = "",

    @SerialName("name")
    val spokenName : String = ""
): Parcelable


