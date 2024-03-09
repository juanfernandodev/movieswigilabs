package com.juanferdev.pruebaingresomovieswigilabs

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.DetailMovieViewModel
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MovieListViewModel
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MoviesRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java))
            return MovieListViewModel(MoviesRepository(context)) as T
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java))
            return DetailMovieViewModel(MoviesRepository(context)) as T

        throw IllegalArgumentException("Unknown class name")
    }

}