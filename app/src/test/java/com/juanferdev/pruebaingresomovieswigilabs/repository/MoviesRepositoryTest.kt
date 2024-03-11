package com.juanferdev.pruebaingresomovieswigilabs.repository

import com.juanferdev.pruebaingresomovieswigilabs.api.ApiService
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.localstore.MovieDAO
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    private val movieDAOMock = mock(MovieDAO::class.java)
    private val apiServiceMock = mock(ApiService::class.java)

    @Test
    fun getAllMoviesWhenTheLocalStoreHasTheMoviesSoEmitAllMoviesFromLocalStore() = runTest {
        //Arrange
        val movieRepository = MoviesRepository(
            apiService = apiServiceMock, movieDAO = movieDAOMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //Act
        val listMovies = movieRepository.getMoviesFlow.toList()
        //Assert
        assert(listMovies.isNotEmpty())
    }

    @Test
    fun getAllMoviesWhenTheLocalStoreHasNotTheMoviesSoEmitAllMoviesFromApi() = runTest {
        //Arrange
        `when`(movieDAOMock.getAllMovies()).thenReturn(emptyList())
        val movieRepository = MoviesRepository(
            apiService = apiServiceMock, movieDAO = movieDAOMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //Act
        val listMovies = movieRepository.getMoviesFlow.toList()
        //Assert
        assert(listMovies.isNotEmpty())
    }

    @Test
    fun getAllMoviesWhenTheLocalStoreHasAnErrorSoEmitStatusError() = runTest {
        //Arrange
        doAnswer { throw Exception() }.`when`(movieDAOMock).getAllMovies()
        val movieRepository = MoviesRepository(
            apiService = apiServiceMock, movieDAO = movieDAOMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //Act
        val listMovies = movieRepository.getMoviesFlow.first()
        //Assert
        assert(listMovies is UiState.Error)
    }

    @Test
    fun getAllMoviesWhenGetFromApiButApiFailSoEmitStatusError() = runTest {
        //Arrange
        `when`(movieDAOMock.getAllMovies()).thenReturn(emptyList())
        doAnswer { throw Exception() }.`when`(apiServiceMock).getAllMovies()
        val movieRepository = MoviesRepository(
            apiService = apiServiceMock, movieDAO = movieDAOMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //Act
        val listMovies = movieRepository.getMoviesFlow.first()
        //Assert
        assert(listMovies is UiState.Error)
    }

    @Test
    fun insertMoviesIntoLocalStoreWhenThrowExceptionSoEmitStatusError() = runTest {
        //Arrange
        `when`(movieDAOMock.getAllMovies()).thenReturn(emptyList())
        val movieRepository = MoviesRepository(
            apiService = apiServiceMock, movieDAO = movieDAOMock,
            dispatcherIO = UnconfinedTestDispatcher()
        )
        //Act
        val listMovies = movieRepository.getMoviesFlow.first()
        //Assert
        assert(listMovies is UiState.Error)
    }
}