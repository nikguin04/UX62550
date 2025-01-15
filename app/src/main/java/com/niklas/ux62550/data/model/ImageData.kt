package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Format: "^    ([a-zA-Z0-9_]+)$"
// Replace: /*     @SerialName("$1")\n    val $1 : TYPE*/

@Serializable
data class ImageDataObject(
    @SerialName("aspect_ratio")
    val aspect_ratio : Float,

    @SerialName("height")
    val height : Int,

    @SerialName("iso_639_1")
    // Internationally accepted 2 letter country codes - https://en.wikipedia.org/wiki/ISO_639-1
    val iso_639_1 : String,

    @SerialName("file_path")
    val file_path : String,

    @SerialName("vote_average")
    val vote_average : Float,

    @SerialName("vote_count")
    val vote_count : Int,

    @SerialName("width")
    val width : Int

)

@Serializable
data class ImagesDataObject(
    @SerialName("id")
    val id: Int,
    @SerialName("backdrops")
    val backdrops: List<ImageDataObject>,
    @SerialName("logos")
    val logos: List<ImageDataObject>,
    @SerialName("posters")
    val posters: List<ImageDataObject>,
) {
    fun getFirstEnBackdrop(): ImageDataObject? {
        backdrops.forEach { backdrop -> if (backdrop.iso_639_1 == "en") { return backdrop } }
        return null
    }

    companion object {
        val EmptyExample = ImagesDataObject(
            id = 0,
            backdrops = listOf(),
            logos = listOf(),
            posters = listOf()
        )
    }

}