package com.juanferdev.pruebaingresomovieswigilabs

import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.ui.movielist.MovieListViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val moviesRepositoryWithMoviesMock = MoviesRepositoryWithMoviesMock()
    private val moviesRepositoryWithErrorMock = MoviesRepositoryWithErrosMock()

    @Test
    fun getAllMoviesWhenIsNotEmptySoIsSuccess() = runTest {
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithMoviesMock)
        movieListViewModel.getAllMoviesFlow()
        assert((movieListViewModel.uiStateFlow.value as UiState.Success).data.isNotEmpty())
    }

    @Test
    fun getAllMoviesWhenIsNotEmptySoStateIsSuccess() = runTest {
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithMoviesMock)
        movieListViewModel.getAllMoviesFlow()
        assert(movieListViewModel.uiStateFlow.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenAllIsGoodSoUiStateIsSuccess() = runTest {
        val movieToUpdate = MoviesMock.movie
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithMoviesMock)
        movieListViewModel.updateMovie(movieToUpdate)
        assert(movieListViewModel.uiStateFlow.value is UiState.Success)
    }

    @Test
    fun updateMovieWhenAllIsGoodSoTheMovieListContainsTheUpdatedMovie() = runTest {
        val movieToUpdate = Movie(
            1,
            "Wolverine",
            "overview",
            posterPath = "posterpath",
            voteAverage = 2.3,
            popularity = 5.0,
            releaseDate = "2024-03-10",
            isFavorite = false
        )
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithMoviesMock)
        movieListViewModel.updateMovie(movieToUpdate)
        val updatedMovieList = (movieListViewModel.uiStateFlow.value as UiState.Success).data
        val updatedMovieListContainsUpdateMovie = updatedMovieList.contains(movieToUpdate)
        assert(updatedMovieListContainsUpdateMovie)
    }

    @Test
    fun getAllMoviesWhenThereIsAnErrorSoUiStateIsError() = runTest {
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithErrorMock)
        movieListViewModel.getAllMoviesFlow()
        assert(movieListViewModel.uiStateFlow.value is UiState.Error)
    }

    @Test
    fun updateMovieWhenThereIsAnErrorSoUiStateIsError() = runTest {
        val movieToUpdate = MoviesMock.movie
        val movieListViewModel = MovieListViewModel(moviesRepositoryWithErrorMock)
        movieListViewModel.updateMovie(movieToUpdate)
        assert(movieListViewModel.uiStateFlow.value is UiState.Error)
    }
}