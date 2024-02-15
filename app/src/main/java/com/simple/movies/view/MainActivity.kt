package com.simple.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.simple.movies.R
import com.simple.movies.adapter.MovieAdapter
import com.simple.movies.databinding.ActivityMainBinding
import com.simple.movies.model.movie.Result
import com.simple.movies.util.Constants
import com.simple.movies.util.interfaces.OnMovieClickListener
import com.simple.movies.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getAllMovies()
        binding.moviesList.layoutManager = GridLayoutManager(applicationContext, 1)
        viewModel.movieLiveData.observe(this) { movies ->
            if (movies != null) {
                binding.moviesList.adapter =
                    MovieAdapter(applicationContext, movies.results, object :
                        OnMovieClickListener {
                        override fun onMovieSelected(movie: Result) {
                            goToDetails(movie.id)
                        }
                    })
                binding.loadingProgressBar.visibility = View.GONE
                LogUtils.eTag(Constants.TAG, R.string.data_received)
            } else {
                LogUtils.eTag(Constants.TAG, R.string.toast_something_went_wrong)
                ToastUtils.showLong(R.string.toast_something_went_wrong)
            }
        }
    }

    //Pass movie ID and go to MovieDetailsActivity activity to show movie details
    private fun goToDetails(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID, movieId)
        startActivity(intent)
    }
}