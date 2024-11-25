package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreObject(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String
)