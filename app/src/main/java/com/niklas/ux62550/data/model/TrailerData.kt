package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerObject(
    @SerialName("id")
        val movieTrailerID : Int,

    @SerialName("results")
    val resultsTrailerLinks : List<TrailerLink>,
)

@Serializable
data class TrailerLink(
    @SerialName("type")
    val type: String? = null,

    @SerialName("key")
    val key: String
)