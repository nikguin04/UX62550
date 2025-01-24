package com.niklas.ux62550.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDataObject(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MediaObject>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,
)

/*
 NOTE: EASY SEARCH AND REPLACE:
 Just write top like "SerialName("adult")"
 Then search and replace with following (REGEX ENABLED)
 ---------------------
(@SerialName\(\"(.+?)\"\))
 ----------------------------
$1
\tval $2: ,
----------------------
And just write the type (this could be made so you don't even need to type @SerialName but i cant be arsed)
*/

// ChatGPT: Recommended Parcelize
@Parcelize
@Serializable
data class MediaObject(
    @SerialName("adult")
    val adult: Boolean = true,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("id")
    val id: Int,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("title")
    val title: String = "",

    @SerialName("overview")
    val overview: String = "",

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("media_type")
    var mediaType: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = listOf(),

    @SerialName("popularity")
    val popularity: Float? = null,

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("vote_average")
    val voteAverage: Float? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("vote_count")
	val voteCount: Int? = null,
): Parcelable {
    fun getUniqueStringIdentifier(): String {
        return (mediaType ?: "") + id
    }
}
