package com.niklas.ux62550.data.remote

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.TrailerObject
import kotlinx.serialization.json.Json
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

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.NONE //HttpLoggingInterceptor.Level.BODY // Logs request and response body
}

class RemoteMediaDataSource {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
        private const val CONTENT_TYPE = "application/json; charset=UTF8"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor("cf1263628c618a27a88c8cec3f0b1d1f")) // Add the interceptor
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    private val mediaApi: TMDBApiService = retrofit.create(TMDBApiService::class.java)

    suspend fun getSearch(search_mode: String, query: String) = mediaApi.getSearch(search_mode, query)
    suspend fun getTrending(time_window: String = "day") = mediaApi.getTrending(time_window)
    suspend fun getKeywordMovies(keyword_id: String, page: Int) = mediaApi.getKeywordMovies(keyword_id,page)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres,page)
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

interface TMDBApiService {

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


class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add API key as a query parameter
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        // Create a new request with the updated URL
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}