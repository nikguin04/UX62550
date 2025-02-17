package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditObject(
    @SerialName("cast")
    val cast: List<Cast>,

    @SerialName("crew")
    val crew: List<Crew>
)

@Serializable
data class Cast(
    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String?,

    @SerialName("character")
    val character: String
)

@Serializable
data class Crew(
    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String?,

    @SerialName("job")
    val job: String
)
