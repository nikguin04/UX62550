package com.niklas.ux62550.data.remote

import com.niklas.ux62550.data.model.CastObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.SimilarMoviesObject
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

    suspend fun getSearch(search_mode: String, query: String) = mediaApi.getSearch(search_mode, query)
    suspend fun getKeywordMovies(keyword_id: String, page: Int) = mediaApi.getKeywordMovies(keyword_id,page)
    suspend fun getDiscoverMovies(genres: String, page: Int) = mediaApi.getDiscoverMovies(genres,page)
	suspend fun getMoviesDetails(movie_id: Int) = mediaApi.getMovieDetails(movie_id)
    suspend fun getSimilarMoviesDetail(movie_id: Int) = mediaApi.getSimilarMovies(movie_id)
    suspend fun getCastDetails(movie_id: Int) = mediaApi.getCast(movie_id)
    suspend fun getProviders(movie_id: Int) = mediaApi.getProvider(movie_id)
    suspend fun getUserSearch(search_query: String) = mediaApi.getUserSearch(search_query)
}

interface TMDBApiService {

    @GET("search/{search_mode}?&page=1&language=en-US")
    suspend fun getSearch(@Path("search_mode") search_mode: String, @Query("query") query: String): SearchDataObject


    @GET("search/movie")
    suspend fun getUserSearch(@Query("query") query: String): SearchDataObject


	@GET("keyword/{keyword_id}/movies")
    suspend fun getKeywordMovies(@Path("keyword_id") keyword_id: String, @Query("page") page: Int = 1): SearchDataObject

    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("with_genres") genres: String, @Query("page") page: Int = 1): SearchDataObject

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): MovieDetailObject

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movie_id: Int): SimilarMoviesObject

    @GET("movie/{movie_id}/credits")
    suspend fun getCast(@Path("movie_id") movie_id: Int): CastObject

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getProvider(@Path("movie_id") movie_id: Int): ProviderDataObject
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