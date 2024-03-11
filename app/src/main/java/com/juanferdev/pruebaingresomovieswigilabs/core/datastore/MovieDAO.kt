package com.juanferdev.pruebaingresomovieswigilabs.core.datastore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    @Insert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)


}