package com.juanferdev.pruebaingresomovieswigilabs.di

import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepository
import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepositoryContract
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