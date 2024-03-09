package com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import coil.load
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.ViewModelFactory
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ActivityDetailMovieBinding
import com.juanferdev.pruebaingresomovieswigilabs.utils.parcelable

const val MOVIE_KEY = "MOVIE_KEY"

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val detailMovieViewModel: DetailMovieViewModel by viewModels {
        ViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent?.parcelable<Movie>(MOVIE_KEY)
        if (movie == null) {
            Toast.makeText(
                this,
                getString(R.string.error_showing_movie_don_t_found), Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }
        updateView(movie)
        initObservers()
        binding.buttonFavorite.setOnClickListener {
            movie.isFavorite = !movie.isFavorite
            detailMovieViewModel.updateMovie(movie)
        }
    }

    private fun initObservers() {
        detailMovieViewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showAlertDialog(uiState.messageId)
                }

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    updateView(uiState.data)
                }
            }

        }
    }

    private fun showAlertDialog(messageId: Int) {
        AlertDialog.Builder(this).setTitle(R.string.there_was_error).setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun updateView(movie: Movie) {
        binding.movie = movie
        binding.buttonFavorite.background = if (movie.isFavorite) {
            AppCompatResources.getDrawable(this, R.drawable.baseline_favorite)
        } else {
            AppCompatResources.getDrawable(this, R.drawable.baseline_no_favorite)
        }
        //binding.imageMovieDetail.load(movie.posterPath)
        binding.imageMovieDetail.load(R.drawable.descarga)
    }
}