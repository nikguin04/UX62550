package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderDataObject(
    @SerialName("id")
    val id: Int,

    @SerialName("results")
    val result: Map<String, Result>
)

@Serializable
data class Result(
    @SerialName("link")
    val link: String,

    @SerialName("rent")
    val rent: List<Provider> = emptyList(),

    @SerialName("flatrate")
    val flatrate: List<Provider> = emptyList(),

    @SerialName("buy")
    val buy: List<Provider> = emptyList()
)

@Serializable
data class Provider(
    @SerialName("logo_path")
    val logoPath: String, // Path to the provider's logo

    @SerialName("provider_id")
    val providerId: Int, // Provider's ID

    @SerialName("provider_name")
    val providerName: String, // Provider's name

    @SerialName("display_priority")
    val displayPriority: Int = 0 // Priority of display
)
