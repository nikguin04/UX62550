package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderDataObject(
    @SerialName("DK")
    val denmarkProvider: DenmarkProvider,

    )
@Serializable
data class DenmarkProvider(
    @SerialName("rent")
    val rentProvider : List<RentProvider>,

    @SerialName("iso_639_1")
    val isoNumber: String,

    @SerialName("name")
    val spokenName : String


)

@Serializable
data class RentProvider(
    @SerialName("logo_path")
    val rentProviderImage : String,

    @SerialName("provider_id")
    val rentProviderId : Int,

    @SerialName("provider_name")
    val rentProviderName : String,

    @SerialName("display_priority")
    val rentProviderDisplay : Int,

    )