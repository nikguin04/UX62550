package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerObject(
    @SerialName("id")
        val movieTrailerID : Int,

    @SerialName("results")
    val resultsTrailerLinks : List<TrailerLinks>,
)

@Serializable
data class TrailerLinks(
    @SerialName("type")
    val type: String? = null,

    @SerialName("key")
    val key: String
)