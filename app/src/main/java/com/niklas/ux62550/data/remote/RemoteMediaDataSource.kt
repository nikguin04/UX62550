package com.niklas.ux62550.data.remote

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.data.model.WatchListDataObject
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File
import javax.inject.Singleton
import kotlin.concurrent.Volatile


class RemoteMediaDataSource(private val retrofit: Retrofit) {
    private val mediaApi: RemoteApiService = retrofit.create(RemoteApiService::class.java)

    suspend fun getSearch(search_mode: String, query: String) = mediaApi.getSearch(search_mode, query)
    suspend fun getTrending(time_window: String = "day") = mediaApi.getTrending(time_window)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres,page)
	suspend fun getMoviesDetails(movie_id: Int) = mediaApi.getMovieDetails(movie_id)
    suspend fun getMovieForRow(movie_id: Int) = mediaApi.getMovieForRow(movie_id)
    suspend fun getSimilarMoviesDetail(movie_id: Int) = mediaApi.getSimilarMovies(movie_id)
    suspend fun getCreditDetails(movie_id: Int) = mediaApi.getCredit(movie_id)
    suspend fun getProviders(movie_id: Int) = mediaApi.getProvider(movie_id)
    suspend fun getMovieGenres() = mediaApi.getMovieGenres()
    suspend fun getTvGenres() = mediaApi.getTvGenres()
    suspend fun getUserSearch(search_query: String) = mediaApi.getUserSearch(search_query)
    suspend fun getTrailer(movie_id: Int) = mediaApi.getTrailer(movie_id)
    suspend fun getImages(media_type: String, media_id: Int,  include_image_language: String = "en") = mediaApi.getImages(media_type, media_id, include_image_language)
}
