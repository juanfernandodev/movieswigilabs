package com.juanferdev.pruebaingresomovieswigilabs.features.movielist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ActivityMovieListBinding
import com.juanferdev.pruebaingresomovieswigilabs.features.UiState
import com.juanferdev.pruebaingresomovieswigilabs.features.detailmovie.ui.DetailMovieActivity
import com.juanferdev.pruebaingresomovieswigilabs.features.detailmovie.ui.MOVIE_KEY
import com.juanferdev.pruebaingresomovieswigilabs.features.movielist.stateholder.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val adapterAllMovies = MoviesAdapter(this)
    private val adapterFavoriteMovies = MoviesAdapter(this)
    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        initRecyclers()
        initObserverFlow()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        movieListViewModel.getAllMoviesFlow()
    }

    private fun initRecyclers() {
        adapterAllMovies.setOnItemClickListener { movie ->
            openDetailMovieActivity(movie)
        }
        adapterFavoriteMovies.setOnItemClickListener { movie ->
            openDetailMovieActivity(movie)
        }

        adapterAllMovies.setOnFavoriteClickListener { movie ->
            movieListViewModel.updateMovie(movie)
        }
        adapterFavoriteMovies.setOnFavoriteClickListener { movie ->
            movieListViewModel.updateMovie(movie)
        }


        val recyclerAllMovies = binding.recycleAllMovies
        recyclerAllMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerAllMovies.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp)))
        recyclerAllMovies.adapter = adapterAllMovies


        val recyclerFavoriteMovies = binding.recycleYourFavorites
        recyclerFavoriteMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerFavoriteMovies.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen._8dp
                )
            )
        )
        recyclerFavoriteMovies.adapter = adapterFavoriteMovies

    }

    private fun openDetailMovieActivity(movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_KEY, movie)
        startActivity(intent)
    }

    private fun initObserverFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            showAlertDialog(uiState.messageId)
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            updateView(uiState.data)

                        }
                    }
                }
            }


        }
    }

    private fun updateView(movies: List<Movie>) {
        adapterAllMovies.submitList(movies.filter { it.isFavorite.not() })
        adapterFavoriteMovies.submitList(movies.filter { it.isFavorite })

    }

    private fun showAlertDialog(messageId: Int) {
        AlertDialog.Builder(this).setTitle(R.string.there_was_error).setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

}