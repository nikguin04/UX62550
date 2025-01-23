package com.niklas.ux62550.data.remote

import retrofit2.Retrofit

class RemoteMediaDataSource(retrofit: Retrofit) {
    private val mediaApi: RemoteApiService = retrofit.create(RemoteApiService::class.java)

    suspend fun getSearch(search_mode: String, query: String) = mediaApi.getSearch(search_mode, query)
    suspend fun getTrending(time_window: String = "day") = mediaApi.getTrending(time_window)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres, page)
    suspend fun getMoviesDetails(movie_id: Int) = mediaApi.getMovieDetails(movie_id)
    suspend fun getSimilarMoviesDetail(movie_id: Int) = mediaApi.getSimilarMovies(movie_id)
    suspend fun getCreditDetails(movie_id: Int) = mediaApi.getCredit(movie_id)
    suspend fun getProviders(movie_id: Int) = mediaApi.getProvider(movie_id)
    suspend fun getMovieGenres() = mediaApi.getMovieGenres()
    suspend fun getTvGenres() = mediaApi.getTvGenres()
    suspend fun getUserSearch(search_query: String) = mediaApi.getUserSearch(search_query)
    suspend fun getTrailer(movie_id: Int) = mediaApi.getTrailer(movie_id)
    suspend fun getImages(media_type: String, media_id: Int,  include_image_language: String = "en") = mediaApi.getImages(media_type, media_id, include_image_language)
}
