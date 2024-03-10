package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.MovieMapper
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _uiState: MutableLiveData<UiState<List<Movie>>> = MutableLiveData()
    val uiState: LiveData<UiState<List<Movie>>>
        get() = _uiState

    fun getAllMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading()
            _uiState.value = moviesRepository.getMovies()
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            val movieEntity = MovieMapper().fromMovieToMovieEntity(movie)
            moviesRepository.updateMovie(movieEntity)
            getAllMovies()
        }
    }
}