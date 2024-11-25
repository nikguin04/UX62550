package com.niklas.ux62550.data.remote

import com.niklas.ux62550.data.model.SearchDataObject
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RemoteMediaDataSource {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
        private const val CONTENT_TYPE = "application/json; charset=UTF8"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor("cf1263628c618a27a88c8cec3f0b1d1f")) // Add the interceptor
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    private val mediaApi: TMDBApiService = retrofit.create(TMDBApiService::class.java)

    suspend fun getMultiSearch(query: String) = mediaApi.getMultiSearch(query)
    suspend fun getKeywordMovies(keyword_id: String, page: Int) = mediaApi.getKeywordMovies(keyword_id,page)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres,page)
}

interface TMDBApiService {

    @GET("search/multi?&page=1&language=en-US")
    suspend fun getMultiSearch(@Query("query") query: String): SearchDataObject

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int)

    @GET("keyword/{keyword_id}/movies")
    suspend fun getKeywordMovies(@Path("keyword_id") keyword_id: String, @Query("page") page: Int = 1): SearchDataObject

    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("with_genres") genres: String, @Query("page") page: Int = 1): SearchDataObject
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