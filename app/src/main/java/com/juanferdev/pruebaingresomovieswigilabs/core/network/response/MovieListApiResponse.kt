package com.juanferdev.pruebaingresomovieswigilabs.core.network.response

import com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos.MovieDTO
import com.squareup.moshi.Json

data class MovieListApiResponse(
    @field:Json(name = "results") val movies: List<MovieDTO>
)