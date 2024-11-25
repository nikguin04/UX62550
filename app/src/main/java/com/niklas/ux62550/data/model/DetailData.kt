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
    val forAdults: Boolean,

    @SerialName("backdrop_path")
    val backDropPath : String,

    @SerialName("homepage")
    val homePage : String,

    @SerialName("imdb_id")
    val imbdID : String,

    @SerialName("original_title")
    val Originaltitle : String,

    @SerialName("overview")
    val Description : String,

    @SerialName("poster_path")
    val posterPath : String,

    @SerialName("release_date")
    val relaseDate : String,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val rating : Double,

    @SerialName("runtime")
    val runTime : Int,

    @SerialName("id")
    val id : Int,

)
@Serializable
data class Genre(
    @SerialName("id")
    val genreID: Int,

    @SerialName("name")
    val genreName: String
)

@Serializable
data class SpokenLanguages(
    @SerialName("english_name")
    val languageName : String,

    @SerialName("iso_639_1")
    val isoNumber: String,

    @SerialName("name")
    val spokenName : String
)

@Serializable
data class Cast(
    @SerialName("name")
    val castName : String,

    @SerialName("profile_path")
    val castProfilePath: String
)
