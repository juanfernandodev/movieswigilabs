package com.juanferdev.pruebaingresomovieswigilabs

import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock.movieList
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntityMapper
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MoviesRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryWithMoviesMock : MoviesRepositoryContract {

    override val getMoviesFlow: Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Success(movieList))
    }

    override suspend fun updateMovie(movieEntityToUpdate: MovieEntity): UiState<Movie> {
        val movieToUpdate = MovieEntityMapper().fromMovieEntityToMovie(movieEntityToUpdate)
        val indexMovie = movieList.indexOfFirst { it.id == movieToUpdate.id }
        movieList[indexMovie] = movieToUpdate
        return UiState.Success(movieToUpdate)
    }

}

class MoviesRepositoryWithErrosMock : MoviesRepositoryContract {

    override val getMoviesFlow: Flow<UiState<List<Movie>>> = flow {
        emit(UiState.Error(R.string.there_was_error))
    }

    override suspend fun updateMovie(movieEntityToUpdate: MovieEntity): UiState<Movie> =
        UiState.Error(R.string.there_was_error)

}