package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.api.ApiService
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTO
import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTOMapper
import com.juanferdev.pruebaingresomovieswigilabs.api.makeNetworkCall
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieDAO
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntityMapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MoviesRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val apiService: ApiService,
    private val movieDAO: MovieDAO
) {

    val getMoviesFlow: Flow<UiState<List<Movie>>> = flow {
        when (val allMoviesLocalResponse = getLocalMovies()) {
            is UiState.Error -> {
                emit(allMoviesLocalResponse)
            }

            is UiState.Success -> {
                if (allMoviesLocalResponse.data.isEmpty()) {
                    emit(getApiMovies())
                } else {
                    emit(allMoviesLocalResponse)
                }
            }

            else -> emit(UiState.Error(R.string.there_was_error))
        }
    }.flowOn(dispatcherIO)

    private suspend fun getLocalMovies(): UiState<List<Movie>> {
        return try {
            val localMoviesEntity = movieDAO.getAllMovies()
            UiState.Success(MovieEntityMapper().fromMovieEntityListToMovieList(localMoviesEntity))
        } catch (e: Exception) {
            UiState.Error(R.string.there_was_error)
        }
    }

    private suspend fun getApiMovies(): UiState<List<Movie>> {
        return makeNetworkCall {
            val movieListResponse = apiService.getAllMovies()
            val movieListDTO = movieListResponse.movies
            insertMoviesLocally(movieListDTO)
            MovieDTOMapper().fromMovieDTOListToMovieModelList(movieListDTO)
        }
    }

    private suspend fun insertMoviesLocally(movieListDTO: List<MovieDTO>) {
        val movieEntityList = MovieDTOMapper().fromMovieDTOListToMovieEntityList(movieListDTO)
        movieDAO.insertMovies(movieEntityList)
    }

    suspend fun updateMovie(movie: MovieEntity): UiState<Movie> {
        return withContext(dispatcherIO) {
            try {
                movieDAO.updateMovie(movie)
                val movieUpdate = MovieEntityMapper().fromMovieEntityToMovie(movie)
                UiState.Success(movieUpdate)
            } catch (e: Exception) {
                UiState.Error(R.string.there_was_error)
            }
        }
    }

}