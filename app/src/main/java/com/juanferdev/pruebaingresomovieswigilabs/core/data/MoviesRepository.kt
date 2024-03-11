package com.juanferdev.pruebaingresomovieswigilabs.core.data

import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieDAO
import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntityMapper
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.core.network.ApiService
import com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos.MovieDTO
import com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos.MovieDTOMapper
import com.juanferdev.pruebaingresomovieswigilabs.core.network.makeNetworkCall
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MoviesRepository @Inject constructor(
    private val dispatcherIO: CoroutineDispatcher,
    private val apiService: ApiService,
    private val movieDAO: MovieDAO
) : MoviesRepositoryContract {

    override val getMoviesFlow = flow {
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

    override suspend fun updateMovie(movieEntityToUpdate: MovieEntity): UiState<Movie> {
        return withContext(dispatcherIO) {
            try {
                movieDAO.updateMovie(movieEntityToUpdate)
                val movieUpdate = MovieEntityMapper().fromMovieEntityToMovie(movieEntityToUpdate)
                UiState.Success(movieUpdate)
            } catch (e: Exception) {
                UiState.Error(R.string.there_was_error)
            }
        }
    }

}