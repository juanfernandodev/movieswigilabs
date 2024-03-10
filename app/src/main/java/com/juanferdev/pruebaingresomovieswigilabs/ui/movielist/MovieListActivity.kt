package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.ViewModelFactory
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ActivityMovieListBinding
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.DetailMovieActivity
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.MOVIE_KEY


class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val adapterAllMovies = MoviesAdapter(this)
    private val adapterFavoriteMovies = MoviesAdapter(this)
    private val movieListViewModel: MovieListViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        initRecyclers()
        initObserver()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        movieListViewModel.getAllMovies()
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

    private fun initObserver() {
        movieListViewModel.uiState.observe(this) { uiState ->
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