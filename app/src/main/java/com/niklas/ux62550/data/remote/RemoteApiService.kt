package com.niklas.ux62550.data.remote

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.data.model.WatchListDataObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {

    @GET("search/{search_mode}?&page=1&language=en-US")
    suspend fun getSearch(@Path("search_mode") search_mode: String, @Query("query") query: String): SearchDataObject

    @GET("trending/movie/{time_window}") // Note: Does not support "people" as search mode
    suspend fun getTrending(@Path("time_window") time_window: String): SearchDataObject


    @GET("search/multi")
    suspend fun getUserSearch(@Query("query") query: String): SearchDataObject


    @GET("keyword/{keyword_id}/movies")
    suspend fun getKeywordMovies(@Path("keyword_id") keyword_id: String, @Query("page") page: Int = 1): SearchDataObject

    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("with_genres") genres: String, @Query("page") page: Int = 1): SearchDataObject

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): MovieDetailObject

    @GET("movie/{movie_id}")
    suspend fun getMovieForRow(@Path("movie_id") movie_id: Int): WatchListDataObject

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movie_id: Int): SearchDataObject

    @GET("movie/{movie_id}/credits")
    suspend fun getCredit(@Path("movie_id") movie_id: Int): CreditObject

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getProvider(@Path("movie_id") movie_id: Int): ProviderDataObject

    @GET("{media_type}/{media_id}/images")
    suspend fun getImages(@Path("media_type") media_type: String, @Path("media_id") media_id: Int, @Query("include_image_language") include_image_language: String): ImagesDataObject

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenreDataObject
    @GET("genre/tv/list")
    suspend fun getTvGenres(): GenreDataObject

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailer(@Path("movie_id") movie_id: Int): TrailerObject
}
