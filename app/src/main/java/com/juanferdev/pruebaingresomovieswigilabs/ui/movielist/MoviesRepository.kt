package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import com.juanferdev.pruebaingresomovieswigilabs.api.MoviesApi
import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTOMapper
import com.juanferdev.pruebaingresomovieswigilabs.api.makeNetworkCall

class MoviesRepository {

    suspend fun getAllMovies() = makeNetworkCall {
        val movieListResponse = MoviesApi.retrofitService.getAllDogs()
        val movieList = movieListResponse.movies
        MovieDTOMapper().fromMovieDTOListToMovieModelList(movieList)
    }

}