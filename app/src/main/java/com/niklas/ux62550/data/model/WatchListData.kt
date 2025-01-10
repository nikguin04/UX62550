package com.niklas.ux62550.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WatchListDataObject(
    @SerialName("backdrop_path")
    val backDropPath : String?,

    @SerialName("homepage")
    val homePage : String,

    @SerialName("original_title")
    val Originaltitle : String,

    @SerialName("poster_path")
    val posterPath : String,

    @SerialName("release_date")
    val relaseDate : String,

    @SerialName("title")
    val title: String,

    @SerialName("vote_average")
    val rating : Double,

    @SerialName("id")
    val id : Int,

    ): Parcelable



