package com.simple.movies.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.simple.movies.R
import com.simple.movies.databinding.ActivityMovieDetailsBinding
import com.simple.movies.util.Constants
import com.simple.movies.viewmodel.MoviesViewModel
import java.util.Locale

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
        binding.back.setOnClickListener { finish() }
        viewModel.movieDetailsData.observe(this) {
            binding.loadingProgressBar.visibility = View.GONE
            binding.movieDetailsLayout.visibility = View.VISIBLE
            loadImage(
                "https://image.tmdb.org/t/p/w500${viewModel.movieDetailsData.value!!.backdropPath}",
                R.drawable.movie_poster_placeholder_landscape,
                binding.movieBackdrop
            )
            loadImage(
                "https://image.tmdb.org/t/p/w500${viewModel.movieDetailsData.value!!.posterPath}",
                R.drawable.movie_poster_placeholder,
                binding.moviePoster
            )
            binding.movieTitle.text = viewModel.movieDetailsData.value!!.title
            binding.movieReleaseDate.text = viewModel.movieDetailsData.value!!.releaseDate
            binding.movieOverview.text = viewModel.movieDetailsData.value!!.overview
            val genres = viewModel.movieDetailsData.value!!.genres
            val genresStringBuilder = StringBuilder()

            genres.forEachIndexed { index, genre ->
                genresStringBuilder.append("[${genre.name.toString().capitalize(Locale.ROOT)}]")
                if (index < genres.size - 1) {
                    genresStringBuilder.append(" ")
                }
            }
            val genresText = genresStringBuilder.toString()
            binding.movieGenres.text = genresText
            LogUtils.eTag(Constants.TAG, R.string.data_received)
        }
    }

    private fun loadImage(
        imageUrl: String,
        placeHolder: Int,
        imageView: ImageView
    ) {

        Glide.with(applicationContext)
            .load(imageUrl)
            .placeholder(placeHolder)
            .into(imageView)
    }
}