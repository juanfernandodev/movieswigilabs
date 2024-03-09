package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import android.content.Context
import android.util.Log
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.api.MoviesApi
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTO
import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTOMapper
import com.juanferdev.pruebaingresomovieswigilabs.api.makeNetworkCall
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntityMapper
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MoviesDataBase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val context: Context,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getMovies(): UiState<List<Movie>> {
        return withContext(dispatcherIO) {
            when (val allMoviesLocalResponse = getLocalMovies()) {
                is UiState.Error -> {
                    allMoviesLocalResponse
                }

                is UiState.Success -> {
                    if (allMoviesLocalResponse.data.isEmpty()) {
                        getApiMovies()
                    } else {
                        allMoviesLocalResponse
                    }
                }

                else -> UiState.Error(R.string.there_was_error)
            }
        }
    }

    private suspend fun getLocalMovies(): UiState<List<Movie>> {
        return try {
            val localMoviesEntity = MoviesDataBase.instance(context).moviesDAO().getAllMovies()
            UiState.Success(MovieEntityMapper().fromMovieEntityListToMovieList(localMoviesEntity))
        } catch (e: Exception) {
            Log.i("Error", e.message ?: String())
            UiState.Error(R.string.there_was_error)
        }
    }

    private suspend fun getApiMovies(): UiState<List<Movie>> {
        return makeNetworkCall {
            val movieListResponse = MoviesApi.retrofitService.getAllMovies()
            val movieListDTO = movieListResponse.movies
            insertMoviesLocally(movieListDTO)
            MovieDTOMapper().fromMovieDTOListToMovieModelList(movieListDTO)
        }
    }

    private suspend fun insertMoviesLocally(movieListDTO: List<MovieDTO>) {
        withContext(dispatcherIO) {
            val movieEntityList = MovieDTOMapper().fromMovieDTOListToMovieEntityList(movieListDTO)
            MoviesDataBase.instance(context).moviesDAO().insertMovies(movieEntityList)
        }
    }

}