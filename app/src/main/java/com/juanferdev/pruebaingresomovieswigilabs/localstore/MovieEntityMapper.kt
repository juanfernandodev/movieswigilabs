package com.juanferdev.pruebaingresomovieswigilabs.localstore

import com.juanferdev.pruebaingresomovieswigilabs.Movie

class MovieEntityMapper {

    fun fromMovieEntityListToMovieList(movieEntities: List<MovieEntity>): List<Movie> {
        val isFavorite = 1
        return movieEntities.map { entity ->
            Movie(
                entity.id, entity.title, entity.overview, entity.posterPath, entity.voteAverage,
                entity.popularity, entity.releaseDate, entity.isFavorite == isFavorite
            )
        }
    }
}