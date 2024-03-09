package com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ActivityDetailMovieBinding
import com.juanferdev.pruebaingresomovieswigilabs.utils.parcelable

const val MOVIE_KEY = "MOVIE_KEY"

class DetailMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailMovieBinding.inflate(layoutInflater)
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
        binding.movie = movie
        //binding.imageMovieDetail.load(movie.posterPath)
        binding.imageMovieDetail.load(R.drawable.descarga)
    }
}