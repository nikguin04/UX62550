package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderDataObject(
    @SerialName("id")
    val id: Int,

    @SerialName("results")
    val result: Map<String,Result>
    )
@Serializable
data class Result(
    @SerialName("link")
    val link: String,

    @SerialName("rent")
    val rent: List<RentProvider> = emptyList(),

    @SerialName("flatrate")
    val flatrate: List<FlatrateProvider> = emptyList(),

    @SerialName("buy")
    val buy: List<BuyProvider> = emptyList()
)

@Serializable
data class DenmarkProvider(
    @SerialName("link")
    val link: String,

    @SerialName("rent")
    val rentProvider : List<RentProvider>,

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