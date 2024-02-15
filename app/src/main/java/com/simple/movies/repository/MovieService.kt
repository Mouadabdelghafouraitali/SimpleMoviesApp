package com.simple.movies.repository


import com.simple.movies.model.movie.Movie
import com.simple.movies.model.moviedetails.MovieDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    /**
     * Retrieves all movies.
     * @param apiKey The API key for authentication.
     * @return Call<Movie> A Retrofit Call object for asynchronous execution.
     */
    @GET("/3/discover/movie")
    fun getAllMovies(@Query("api_key") apiKey: String): Call<Movie>

    /**
     * Retrieves details of a specific movie.
     * @param movieId The ID of the movie.
     * @param apiKey The API key for authentication.
     * @return Call<MovieDetails> A Retrofit Call object for asynchronous execution.
     */
    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetails>
}