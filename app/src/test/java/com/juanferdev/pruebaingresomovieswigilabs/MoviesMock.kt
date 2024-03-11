package com.juanferdev.pruebaingresomovieswigilabs

object MoviesMock {
    val movie = Movie(
        1,
        "Wolverine",
        "overview",
        posterPath = "posterpath",
        voteAverage = 2.3,
        popularity = 5.0,
        releaseDate = "2024-03-10",
        isFavorite = false
    )

    val movieList = mutableListOf(
        movie,
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
}