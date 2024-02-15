package com.simple.movies.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.simple.movies.R
import com.simple.movies.databinding.ActivityMovieDetailsBinding
import com.simple.movies.util.Constants
import com.simple.movies.viewmodel.MoviesViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        val movieId = intent.getIntExtra(Constants.MOVIE_ID, 0)
        viewModel.getMovieDetails(movieId)
        viewModel.movieDetailsData.observe(this) {
            binding.loadingProgressBar.visibility = View.GONE
            binding.movieDetailsLayout.visibility = View.VISIBLE
            Glide.with(applicationContext)
                .load("https://image.tmdb.org/t/p/w500${viewModel.movieDetailsData.value!!.posterPath}")
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(binding.moviePoster)
            binding.movieTitle.text = viewModel.movieDetailsData.value!!.title
            binding.movieReleaseDate.text = viewModel.movieDetailsData.value!!.releaseDate
            binding.movieOverview.text = viewModel.movieDetailsData.value!!.overview
            LogUtils.eTag(Constants.TAG, R.string.data_received)
        }
    }
}