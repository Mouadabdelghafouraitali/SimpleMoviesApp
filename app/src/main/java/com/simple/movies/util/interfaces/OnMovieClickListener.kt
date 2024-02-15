package com.simple.movies.util.interfaces

import com.simple.movies.model.movie.Result

//Interface to handle the user movie selection
interface OnMovieClickListener {
    fun onMovieSelected(movie: Result)
}