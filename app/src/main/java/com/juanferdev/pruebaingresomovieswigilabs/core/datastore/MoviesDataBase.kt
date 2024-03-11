package com.juanferdev.pruebaingresomovieswigilabs.core.datastore

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDAO(): MovieDAO

}