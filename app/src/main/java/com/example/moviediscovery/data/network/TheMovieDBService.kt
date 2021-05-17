package com.example.moviediscovery.data.network

import android.app.Application
import com.example.moviediscovery.data.model.genre.Genres
import com.example.moviediscovery.data.model.movie.Movie
import com.example.moviediscovery.data.model.movie.MovieDiscover
import com.example.moviediscovery.data.model.movie.video.Video
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {
    @GET("genre/movie/list")
    suspend fun fetchGenres(): Genres

    @GET("movie/{movieId}")
    suspend fun fetchMovieDetails(@Path("movieId") movieId: Int): Movie

    @GET("discover/movie")
    suspend fun fetchDiscoverMovie(@Query("page") page: Int): MovieDiscover

    @GET("movie/{movieId}/videos")
    suspend fun fetchMovieVideo(@Path("movieId") movieId: Int): Video

    @GET("discover/movie")
    suspend fun fetchDiscoverMovieWithGenres(@Query("with_genres") genres: String, @Query("page") page: Int): MovieDiscover

    companion object {
        operator fun invoke(app: Application): TheMovieDBService {
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(APIKeyInterceptor())
                .addInterceptor(ConnectivityInterceptor(app.applicationContext))
                .build()

            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(THE_MOVIE_DB_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TheMovieDBService::class.java)
        }
    }
}