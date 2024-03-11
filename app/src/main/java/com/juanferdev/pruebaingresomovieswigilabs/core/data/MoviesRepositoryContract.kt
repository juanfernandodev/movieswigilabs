package com.juanferdev.pruebaingresomovieswigilabs.core.data

import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import kotlinx.coroutines.flow.Flow

interface MoviesRepositoryContract {
    val getMoviesFlow: Flow<UiState<List<Movie>>>

    suspend fun updateMovie(movieEntityToUpdate: MovieEntity): UiState<Movie>
}