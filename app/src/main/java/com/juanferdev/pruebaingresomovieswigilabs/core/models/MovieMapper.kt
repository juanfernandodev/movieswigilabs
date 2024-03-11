package com.juanferdev.pruebaingresomovieswigilabs.core.models

import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntity

class MovieMapper {

    fun fromMovieToMovieEntity(movie: Movie) =
        MovieEntity(
            movie.id,
            movie.title,
            movie.overview,
            movie.posterPath,
            movie.voteAverage,
            movie.popularity,
            movie.releaseDate,
            if (movie.isFavorite) 1 else 0
        )
}