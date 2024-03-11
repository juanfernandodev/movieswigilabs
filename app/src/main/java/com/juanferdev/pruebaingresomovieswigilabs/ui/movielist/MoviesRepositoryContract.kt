package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesRepositoryContract {
    val getMoviesFlow: Flow<UiState<List<Movie>>>

    suspend fun updateMovie(movieEntityToUpdate: MovieEntity): UiState<Movie>
}