package com.niklas.ux62550.di

import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.CastDetailsRepository
import com.niklas.ux62550.domain.DiscoverRepository
import com.niklas.ux62550.domain.HomeRepository
import com.niklas.ux62550.domain.ImageRepository
import com.niklas.ux62550.domain.MediaDetailRepository
import com.niklas.ux62550.domain.MediaExtendedDetailsRepository
import com.niklas.ux62550.domain.ProfileRepository
import com.niklas.ux62550.domain.ReviewRepository
import com.niklas.ux62550.domain.SearchRepository
import com.niklas.ux62550.domain.WatchListRepository
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File

object DataModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
    private const val CONTENT_TYPE = "application/json; charset=UTF8"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val cacheSize = 25L * 1024 * 1024 // 25 MB
    private val cache = Cache(File("http_cache"), cacheSize)
    private val CacheInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        // Cache data for 1 hour
        val maxAge = 60 * 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .build()
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE //HttpLoggingInterceptor.Level.BODY // Logs request and response body
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(ApiKeyInterceptor("cf1263628c618a27a88c8cec3f0b1d1f")) // Add the interceptor
        .addInterceptor(loggingInterceptor)
        .addInterceptor(CacheInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()



    private class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
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

    private val remoteMediaDataSource = RemoteMediaDataSource(retrofit)
    private val remoteFirebaseDataSource = RemoteFirebase

    lateinit var castDetailsRepository: CastDetailsRepository
    lateinit var discoverRepository: DiscoverRepository
    lateinit var homeRepository: HomeRepository
    lateinit var imageRepository: ImageRepository
    lateinit var mediaExtendedDetailsRepository: MediaExtendedDetailsRepository
    lateinit var mediaDetailRepository: MediaDetailRepository
    lateinit var reviewRepository: ReviewRepository
    lateinit var searchRepository: SearchRepository
    lateinit var watchListRepository: WatchListRepository
    lateinit var profileRepository : ProfileRepository
    
    fun initialize() {
        castDetailsRepository = CastDetailsRepository(remoteDataSource = remoteMediaDataSource)
        discoverRepository = DiscoverRepository(remoteDataSource = remoteMediaDataSource)
        homeRepository = HomeRepository(remoteDataSource = remoteMediaDataSource)
        imageRepository = ImageRepository(remoteDataSource = remoteMediaDataSource)
        mediaExtendedDetailsRepository = MediaExtendedDetailsRepository(remoteDataSource = remoteMediaDataSource, firebaseDataSource = remoteFirebaseDataSource)
        mediaDetailRepository = MediaDetailRepository(remoteDataSource = remoteMediaDataSource)
        reviewRepository = ReviewRepository(firebaseDataSource = remoteFirebaseDataSource)
        searchRepository = SearchRepository(remoteDataSource = remoteMediaDataSource)
        watchListRepository = WatchListRepository(remoteDataSource = remoteMediaDataSource, firebaseDataSource = remoteFirebaseDataSource)
        profileRepository = ProfileRepository(remoteFirebaseDataSource)
    }
}