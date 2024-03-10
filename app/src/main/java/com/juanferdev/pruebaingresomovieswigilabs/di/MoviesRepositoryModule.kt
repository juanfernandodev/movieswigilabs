package com.juanferdev.pruebaingresomovieswigilabs.di

import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MoviesRepository
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MoviesRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
fun interface MoviesRepositoryContractModule {

    @Binds
    fun bindMoviesRepositoryContract(
        moviesRepository: MoviesRepository
    ): MoviesRepositoryContract

}