package com.simple.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simple.movies.model.movie.Movie
import com.simple.movies.model.moviedetails.MovieDetails
import com.simple.movies.repository.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class responsible for managing movie data and interactions.
 */
class MoviesViewModel : ViewModel() {
    private val _movieLiveData = MutableLiveData<Movie>() // LiveData for storing movie data
    val movieLiveData: LiveData<Movie> // Exposed LiveData for observing movie data
        get() = _movieLiveData

    private val _movieDetailsData =
        MutableLiveData<MovieDetails>() // LiveData for storing movie details data
    val movieDetailsData: MutableLiveData<MovieDetails> // Exposed LiveData for observing movie details data
        get() = _movieDetailsData

    private val retrofitHelper = RetrofitHelper() // RetrofitHelper instance for making API calls

    /**
     * Retrieves all movies.
     */
    fun getAllMovies() {
        retrofitHelper.getAllMovies(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) _movieLiveData.value =
                    response.body() // Set movieLiveData value if response is successful
                else _movieLiveData.value =
                    null // Set movieLiveData value to null if response is not successful
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                _movieLiveData.value = null // Set movieLiveData value to null in case of failure
            }
        })
    }

    /**
     * Retrieves details of a specific movie.
     * @param movieId The ID of the movie.
     */
    fun getMovieDetails(movieId: Int) {
        retrofitHelper.getMovieDetails(movieId, object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) _movieDetailsData.value =
                    response.body() // Set movieDetailsData value if response is successful
                else _movieDetailsData.value =
                    null // Set movieDetailsData value to null if response is not successful
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                _movieDetailsData.value =
                    null // Set movieDetailsData value to null in case of failure
            }
        })
    }
}
