package com.juanferdev.pruebaingresomovieswigilabs

import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntity
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.core.network.dtos.MovieDTO

object MoviesMock {
    val movieMock = Movie(
        1,
        "Wolverine",
        "overview",
        posterPath = "posterpath",
        voteAverage = 2.3,
        popularity = 5.0,
        releaseDate = "2024-03-10",
        isFavorite = false
    )

    val movieListMock = mutableListOf(
        movieMock,
        Movie(
            2,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10",
            isFavorite = false
        )
    )

    val movieDTOListMock = mutableListOf(
        MovieDTO(
            1,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10"
        ),
        MovieDTO(
            2,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10"
        )
    )


    val moviesEntityListMock = mutableListOf(
        MovieEntity(
            1,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10",
            isFavorite = 1
        ),
        MovieEntity(
            2,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10",
            isFavorite = 0
        )
    )

    val movieToUpdateMock = Movie(
        1,
        "Wolverine",
        "overview",
        posterPath = "posterpath",
        voteAverage = 2.3,
        popularity = 5.0,
        releaseDate = "2024-03-10",
        isFavorite = false
    )

    val movieToUpdateEntityMock = MovieEntity(
        1,
        "Wolverine",
        "overview",
        posterPath = "posterpath",
        voteAverage = 2.3,
        popularity = 5.0,
        releaseDate = "2024-03-10",
        isFavorite = 0
    )
}