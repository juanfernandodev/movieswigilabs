package com.juanferdev.pruebaingresomovieswigilabs.di

import android.content.Context
import androidx.room.Room
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieDAO
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MoviesDataBase {
        return Room.databaseBuilder(
            context,
            MoviesDataBase::class.java, "MoviesDataBase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(appDatabase: MoviesDataBase): MovieDAO {
        return appDatabase.moviesDAO()
    }
}