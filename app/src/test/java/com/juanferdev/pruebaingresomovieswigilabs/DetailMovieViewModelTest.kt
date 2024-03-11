package com.juanferdev.pruebaingresomovieswigilabs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.DetailMovieViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailMovieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val moviesRepositoryWithMoviesMock = MoviesRepositoryWithMoviesMock()
    private val moviesRepositoryWithErrorMock = MoviesRepositoryWithErrorMock()

    @Test
    fun updateMovieWhenAllIsGoodSoUiStateIsSuccess() = runTest {
        val movieToUpdate = MoviesMock.movie
        val detailMovieViewModel = DetailMovieViewModel(moviesRepositoryWithMoviesMock)
        detailMovieViewModel.updateMovie(movieToUpdate)
        assert(detailMovieViewModel.uiState.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenThereIsAnErrorSoUiStateIsError() = runTest {
        val movieToUpdate = MoviesMock.movie
        val detailMovieViewModel = DetailMovieViewModel(moviesRepositoryWithErrorMock)
        detailMovieViewModel.updateMovie(movieToUpdate)
        assert(detailMovieViewModel.uiState.value is UiState.Error)
    }
}