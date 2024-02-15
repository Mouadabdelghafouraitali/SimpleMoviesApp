package com.simple.movies.repository

import com.simple.movies.model.movie.Movie
import com.simple.movies.model.moviedetails.MovieDetails
import com.simple.movies.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Helper class to manage Retrofit instance and perform movie service requests.
 */
class RetrofitHelper {
    private var movieService: MovieService
    private var retrofit: Retrofit
    private var builder: OkHttpClient.Builder = OkHttpClient.Builder()


    // Initialization block to set up Retrofit instance and MovieService interface
    init {
        builder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        retrofit = Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
        movieService = retrofit.create(MovieService::class.java)
    }

    /**
     * Retrieves all movies.
     * @param callback Callback for handling the response.
     */
    fun getAllMovies(callback: Callback<Movie>) {
        movieService.getAllMovies(Constants.API_KEY).enqueue(callback)
    }

    /**
     * Retrieves details of a specific movie.
     * @param movieId The ID of the movie.
     * @param callback Callback for handling the response.
     */
    fun getMovieDetails(movieId: Int, callback: Callback<MovieDetails>) {
        movieService.getMovieDetails(movieId, Constants.API_KEY).enqueue(callback)
    }
}