package com.juanferdev.pruebaingresomovieswigilabs.api.response

import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTO

data class MovieListResponse(
    val movieList: List<MovieDTO>
)