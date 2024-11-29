package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderDataObject(
    @SerialName("DK")
    val denmarkProvider: List<DenmarkProvider>,

    )
@Serializable
data class DenmarkProvider(
    @SerialName("rent")
    val rentProvider : List<RentProvider>,

    @SerialName("iso_639_1")
    val isoNumber: String,

    @SerialName("name")
    val spokenName : String,

    @SerialName("flatrate")
    val flatrateProvider : List<FlatrateProvider>,

    @SerialName("buy")
    val buyProvider : List<BuyProvider>,


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
@Serializable
data class FlatrateProvider(
    @SerialName("logo_path")
    val flatrateProviderImage : String,

    @SerialName("provider_id")
    val flatrateProviderId : Int,

    @SerialName("provider_name")
    val flatrateProviderName : String,

    @SerialName("display_priority")
    val flatrateProviderDisplay : Int,

    )
@Serializable
data class BuyProvider(
    @SerialName("logo_path")
    val buyProviderImage : String,

    @SerialName("provider_id")
    val buyProviderId : Int,

    @SerialName("provider_name")
    val buyProviderName : String,

    @SerialName("display_priority")
    val buyProviderDisplay : Int,

    )