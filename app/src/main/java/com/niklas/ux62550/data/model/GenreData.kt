package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class GenreType {
    MOVIE, TV
}

@Serializable
data class GenreDataObject(
    @SerialName("genres")
    val genres: List<GenreObject>
)

@Serializable
data class GenreObject(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)