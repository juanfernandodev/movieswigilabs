package com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos

import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie

class MovieDTOMapper {

    fun fromMovieDTOListToMovieModelList(movieDTO: List<MovieDTO>): List<Movie> {
        return movieDTO.map { dto ->
            Movie(
                dto.id,
                dto.title,
                dto.overview,
                dto.posterPath,
                dto.voteAverage,
                dto.popularity,
                dto.releaseDate,
                isFavorite = false
            )
        }
    }

    fun fromMovieDTOListToMovieEntityList(movieDTO: List<MovieDTO>): List<MovieEntity> {
        val isNotFavorite = 0
        return movieDTO.map { dto ->
            MovieEntity(
                dto.id,
                dto.title,
                dto.overview,
                dto.posterPath,
                dto.voteAverage,
                dto.popularity,
                dto.releaseDate,
                isFavorite = isNotFavorite
            )
        }
    }
}