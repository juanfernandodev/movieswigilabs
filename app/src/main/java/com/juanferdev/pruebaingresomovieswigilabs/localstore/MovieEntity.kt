package com.juanferdev.pruebaingresomovieswigilabs.localstore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val imagePath: String,
    val voteAverage: Double,
    val popularity: Double,
    val releaseDate: String,
    val isFavorite: Int
)