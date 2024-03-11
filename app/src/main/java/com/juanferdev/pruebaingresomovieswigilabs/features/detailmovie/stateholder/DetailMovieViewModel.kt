package com.juanferdev.pruebaingresomovieswigilabs.features.detailmovie.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepositoryContract
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.core.models.MovieMapper
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val repository: MoviesRepositoryContract) :
    ViewModel() {

    private var _uiState: MutableLiveData<UiState<Movie>> = MutableLiveData()
    val uiState: LiveData<UiState<Movie>>
        get() = _uiState

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            val movieEntity = MovieMapper().fromMovieToMovieEntity(movie)
            val uiState = repository.updateMovie(movieEntity)
            _uiState.value = uiState
        }
    }

}