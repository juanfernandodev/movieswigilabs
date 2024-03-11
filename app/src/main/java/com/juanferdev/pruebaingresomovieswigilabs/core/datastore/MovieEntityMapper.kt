package com.juanferdev.pruebaingresomovieswigilabs.core.datastore

import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie

class MovieEntityMapper {

    fun fromMovieEntityListToMovieList(movieEntities: List<MovieEntity>): List<Movie> {
        return movieEntities.map { entity ->
            fromMovieEntityToMovie(entity)
        }
    }

    fun fromMovieEntityToMovie(movieEntity: MovieEntity): Movie {
        val isFavorite = 1
        return Movie(
            movieEntity.id,
            movieEntity.title,
            movieEntity.overview,
            movieEntity.posterPath,
            movieEntity.voteAverage,
            movieEntity.popularity,
            movieEntity.releaseDate,
            movieEntity.isFavorite == isFavorite
        )
    }
}