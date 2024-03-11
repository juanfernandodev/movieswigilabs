package com.juanferdev.pruebaingresomovieswigilabs.features.movielist.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepositoryContract
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.core.models.MovieMapper
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryContract
) : ViewModel() {

    private var _uiStateFlow = MutableStateFlow<UiState<List<Movie>>>(UiState.Success(emptyList()))
    val uiStateFlow: StateFlow<UiState<List<Movie>>>
        get() = _uiStateFlow

    fun getAllMoviesFlow() {
        viewModelScope.launch {
            moviesRepository.getMoviesFlow.collect { uiState ->
                _uiStateFlow.value = uiState
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            val movieEntity = MovieMapper().fromMovieToMovieEntity(movie)
            moviesRepository.updateMovie(movieEntity)
            getAllMoviesFlow()
        }
    }
}