package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieDetailObject(
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguages>,

    @SerialName("genres")
    val genre: List<Genre>,

    @SerialName("adult")
    val adult: Boolean = true,

    @SerialName("backdrop_path")
    val backDropPath : String? = null,

    @SerialName("homepage")
    val homePage : String? = null,

    @SerialName("imdb_id")
    val imbdID : String? = null,

    @SerialName("original_title")
    val Originaltitle : String = "",

    @SerialName("overview")
    val Description : String = "",

    @SerialName("poster_path")
    val posterPath : String?,

    @SerialName("release_date")
    val relaseDate : String,

    @SerialName("title")
    val title: String = "",

    @SerialName("vote_average")
    val rating : Double,

    @SerialName("runtime")
    val runTime : Int = 0,

    @SerialName("id")
    val id : Int,

) {
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

@Serializable
data class Genre(
    @SerialName("id")
    val genreID: Int = 0,

    @SerialName("name")
    val genreName: String = ""
)

@Serializable
data class SpokenLanguages(
    @SerialName("english_name")
    val languageName : String = "",

    @SerialName("iso_639_1")
    val isoNumber: String = "",

    @SerialName("name")
    val spokenName : String = ""
)


