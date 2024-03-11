package com.juanferdev.pruebaingresomovieswigilabs.viewmodels

import com.juanferdev.pruebaingresomovieswigilabs.MainDispatcherRule
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock.movieMock
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock.movieToUpdateEntityMock
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock.movieToUpdateMock
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepositoryContract
import com.juanferdev.pruebaingresomovieswigilabs.core.datastore.MovieEntityMapper
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import com.juanferdev.pruebaingresomovieswigilabs.features.movielist.stateholder.MovieListViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val moviesRepositoryMock = mock(MoviesRepositoryContract::class.java)

    @Test
    fun getAllMoviesWhenIsNotEmptySoIsSuccess() = runTest {
        //Arrange
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Success(MoviesMock.movieListMock))
            }
        )
        //Act
        movieListViewModel.getAllMoviesFlow()
        //Assert
        assert((movieListViewModel.uiStateFlow.value as UiState.Success).data.isNotEmpty())
    }

    @Test
    fun getAllMoviesWhenIsNotEmptySoStateIsSuccess() = runTest {
        //Arrange
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Success(MoviesMock.movieListMock))
            }
        )
        //Act
        movieListViewModel.getAllMoviesFlow()
        //Assert
        assert(movieListViewModel.uiStateFlow.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenAllIsGoodSoUiStateIsSuccess() = runTest {
        //Arrange
        val movieToUpdate = movieMock
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        `when`(moviesRepositoryMock.updateMovie(movieToUpdateEntityMock)).thenReturn(
            UiState.Success(movieToUpdate)
        )
        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Success(MoviesMock.movieListMock))
            }
        )
        //Act
        movieListViewModel.updateMovie(movieToUpdate)
        //Assert
        assert(movieListViewModel.uiStateFlow.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenAllIsGoodSoTheMovieListContainsTheUpdatedMovie() = runTest {
        //Arrange
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        doAnswer {
            val movieToUpdate = MovieEntityMapper().fromMovieEntityToMovie(movieToUpdateEntityMock)
            val indexMovie = MoviesMock.movieListMock.indexOfFirst { it.id == movieToUpdate.id }
            MoviesMock.movieListMock[indexMovie] = movieToUpdate
            UiState.Success(movieToUpdate)
        }.`when`(moviesRepositoryMock).updateMovie(movieToUpdateEntityMock)

        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Success(MoviesMock.movieListMock))
            }
        )
        //Act
        movieListViewModel.updateMovie(movieToUpdateMock)
        val updatedMovieList = (movieListViewModel.uiStateFlow.value as UiState.Success).data
        val updatedMovieListContainsUpdateMovie = updatedMovieList.contains(movieToUpdateMock)
        //Assert
        assert(updatedMovieListContainsUpdateMovie)
    }

    @Test
    fun getAllMoviesWhenThereIsAnErrorSoUiStateIsError() = runTest {
        //Arrange
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Error(R.string.there_was_error))
            }
        )
        //Act
        movieListViewModel.getAllMoviesFlow()
        //Assert
        assert(movieListViewModel.uiStateFlow.value is UiState.Error)
    }

    @Test
    fun updateMovieWhenThereIsAnErrorSoUiStateIsError() = runTest {
        //Arrange
        val movieToUpdate = movieMock
        val movieListViewModel = MovieListViewModel(moviesRepositoryMock)
        `when`(moviesRepositoryMock.getMoviesFlow).thenReturn(
            flow {
                emit(UiState.Error(R.string.there_was_error))
            }
        )
        `when`(moviesRepositoryMock.updateMovie(movieToUpdateEntityMock)).thenReturn(
            UiState.Error(R.string.there_was_error)
        )
        //Act
        movieListViewModel.updateMovie(movieToUpdate)
        //Assert
        assert(movieListViewModel.uiStateFlow.value is UiState.Error)
    }
}