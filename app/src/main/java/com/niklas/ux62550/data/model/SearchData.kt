package com.niklas.ux62550.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDataObject(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MediaObject>,

    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
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

@Serializable
data class MediaObject(
    @SerialName("adult")
	val adult: Boolean = true,

    @SerialName("backdrop_path")
	val backdrop_path: String? = null,

    @SerialName("id")
	val id: Int,

    @SerialName("title")
	val title: String = "",

    @SerialName("overview")
	val overview: String = "",

    @SerialName("poster_path")
	val poster_path: String? = null,

    @SerialName("media_type")
	val media_type: String? = null,

    @SerialName("genre_ids")
	val genre_ids: List<Int> = listOf(),

    @SerialName("popularity")
	val popularity: Float,

    @SerialName("release_date")
	val release_date: String? = null,

    @SerialName("vote_average")
	val vote_average: Float? = null,

    @SerialName("vote_count")
	val vote_count: Int? = null,

    )