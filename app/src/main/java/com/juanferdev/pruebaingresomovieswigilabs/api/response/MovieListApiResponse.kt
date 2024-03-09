package com.juanferdev.pruebaingresomovieswigilabs.api.response

import com.juanferdev.pruebaingresomovieswigilabs.api.dtos.MovieDTO
import com.squareup.moshi.Json

data class MovieListApiResponse(
    @field:Json(name = "results") val movies: List<MovieDTO>
)