package com.juanferdev.pruebaingresomovieswigilabs.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.pruebaingresomovieswigilabs.MainDispatcherRule
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock
import com.juanferdev.pruebaingresomovieswigilabs.MoviesMock.movieMock
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.core.data.MoviesRepositoryContract
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import com.juanferdev.pruebaingresomovieswigilabs.features.detailmovie.stateholder.DetailMovieViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val moviesRepositoryMock = Mockito.mock(MoviesRepositoryContract::class.java)

    @Test
    fun updateMovieWhenAllIsGoodSoUiStateIsSuccess() = runTest {
        //Arrange
        val movieToUpdate = movieMock
        val detailMovieViewModel = DetailMovieViewModel(moviesRepositoryMock)
        Mockito.`when`(moviesRepositoryMock.updateMovie(MoviesMock.movieToUpdateEntityMock))
            .thenReturn(
                UiState.Success(movieToUpdate)
            )
        //Act
        detailMovieViewModel.updateMovie(movieToUpdate)
        //Assert
        assert(detailMovieViewModel.uiState.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenThereIsAnErrorSoUiStateIsError() = runTest {
        //Arrange
        val movieToUpdate = movieMock
        val detailMovieViewModel = DetailMovieViewModel(moviesRepositoryMock)
        Mockito.`when`(moviesRepositoryMock.updateMovie(MoviesMock.movieToUpdateEntityMock))
            .thenReturn(
                UiState.Error(R.string.there_was_error)
            )
        //Act
        detailMovieViewModel.updateMovie(movieToUpdate)
        //Assert
        assert(detailMovieViewModel.uiState.value is UiState.Error)
    }
}