package com.simple.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simple.movies.R
import com.simple.movies.model.movie.Result
import com.simple.movies.util.interfaces.OnMovieClickListener


class MovieAdapter(
    private val context: Context,
    private val movies: List<Result>, // List of movies to be displayed
    private val onMovieClickListener: OnMovieClickListener // Listener for movie click events
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Inflates the layout for the movie item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    // Binds data to the views in the movie item
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    // Returns the number of movies in the list
    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView =
            itemView.findViewById(R.id.moviePoster)
        private val movieTitle: TextView =
            itemView.findViewById(R.id.movieTitle)
        private val movieReleaseDate: TextView =
            itemView.findViewById(R.id.movieReleaseDate)
        private val movieOverview: TextView =
            itemView.findViewById(R.id.movieOverview)

        // Binds movie data to views
        fun bind(movie: Result) {
            // Loading the movie poster using Glide library [https://github.com/bumptech/glide]
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(moviePoster)
            movieTitle.text = movie.title // Setting movie title
            movieReleaseDate.text = movie.releaseDate // Setting movie release date
            movieOverview.text = movie.overview // Setting movie overview
            // Sets click listener to the whole movie item view to handle movie selection
            itemView.setOnClickListener { onMovieClickListener.onMovieSelected(movie) }
        }
    }
}

