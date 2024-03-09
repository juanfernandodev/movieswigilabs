package com.juanferdev.pruebaingresomovieswigilabs.localstore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDAO(): MovieDAO

    companion object {

        private var INSTACE: MoviesDataBase? = null

        fun instanciarBaseDeDatos(contexto: Context): MoviesDataBase {

            return INSTACE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    contexto,
                    MoviesDataBase::class.java, "MoviesDataBase"
                ).fallbackToDestructiveMigration().build()
                INSTACE = instance
                instance
            }
        }
    }
}