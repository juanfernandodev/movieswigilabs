package com.juanferdev.pruebaingresomovieswigilabs.api.dtos

import com.juanferdev.pruebaingresomovieswigilabs.Movie

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
                dto.releaseDate
            )
        }
    }
}