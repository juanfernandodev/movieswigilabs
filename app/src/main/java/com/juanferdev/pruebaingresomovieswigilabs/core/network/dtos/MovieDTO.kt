package com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos

import com.squareup.moshi.Json

data class MovieDTO(
    val id: Long,
    val title: String,
    val overview: String,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "vote_average") val voteAverage: Double,
    val popularity: Double,
    @field:Json(name = "release_date") val releaseDate: String
)
