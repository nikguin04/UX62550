package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerObject(
    @SerialName("id")
    val id: Int,

    @SerialName("results")
    val results: List<TrailerLink>,
)

@Serializable
data class TrailerLink(
    @SerialName("type")
    val type: String? = null,

    @SerialName("key")
    val key: String
)
