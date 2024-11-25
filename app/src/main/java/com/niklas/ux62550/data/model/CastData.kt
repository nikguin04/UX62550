package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastObject(
    @SerialName("cast")
    val cast : List<Cast>
)
@Serializable
data class Cast(
    @SerialName("name")
    val castName : String,

    @SerialName("profile_path")
    val castProfilePath: String?,

    @SerialName("character")
    val character: String
)
