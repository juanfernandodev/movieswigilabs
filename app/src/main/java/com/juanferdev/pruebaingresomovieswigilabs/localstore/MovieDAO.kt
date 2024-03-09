package com.juanferdev.pruebaingresomovieswigilabs.localstore

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    suspend fun getFavoriteMovies(): List<MovieEntity>
}