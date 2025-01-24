package com.niklas.ux62550.data.remote

import retrofit2.Retrofit

class RemoteMediaDataSource(retrofit: Retrofit) {
    private val mediaApi: RemoteApiService = retrofit.create(RemoteApiService::class.java)

    suspend fun getSearch(searchMode: String, query: String) = mediaApi.getSearch(searchMode, query)
    suspend fun getTrending(timeWindow: String = "day") = mediaApi.getTrending(timeWindow)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres, page)
    suspend fun getMoviesDetails(movieId: Int) = mediaApi.getMovieDetails(movieId)
    suspend fun getSimilarMoviesDetail(movieId: Int) = mediaApi.getSimilarMovies(movieId)
    suspend fun getCreditDetails(movieId: Int) = mediaApi.getCredit(movieId)
    suspend fun getProviders(movieId: Int) = mediaApi.getProvider(movieId)
    suspend fun getMovieGenres() = mediaApi.getMovieGenres()
    suspend fun getTvGenres() = mediaApi.getTvGenres()
    suspend fun getUserSearch(searchQuery: String) = mediaApi.getUserSearch(searchQuery)
    suspend fun getTrailer(movieId: Int) = mediaApi.getTrailer(movieId)
    suspend fun getImages(mediaType: String, mediaId: Int, includeImageLanguage: String = "en") = mediaApi.getImages(mediaType, mediaId, includeImageLanguage)
}
