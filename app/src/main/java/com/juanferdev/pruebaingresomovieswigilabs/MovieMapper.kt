package com.juanferdev.pruebaingresomovieswigilabs

import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieEntity

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