package com.juanferdev.pruebaingresomovieswigilabs

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val popularity: Double,
    val releaseDate: String
)
