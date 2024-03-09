package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.api.UiState
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ActivityMovieListBinding
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.DetailMovieActivity
import com.juanferdev.pruebaingresomovieswigilabs.ui.detailmovie.MOVIE_KEY


class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val adapterAllMovies = AllMoviesAdapter()
    private val movieListViewModel: MovieListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        initRecyclerAllMovies()
        initObserver()
        setContentView(binding.root)
    }

    private fun initRecyclerAllMovies() {
        adapterAllMovies.setOnItemClickListener { movie ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(MOVIE_KEY, movie)
            startActivity(intent)
        }

        val recycler = binding.recycleAllMovies
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = adapterAllMovies
        recycler.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp))
        )
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
                    adapterAllMovies.submitList(uiState.data)
                }
            }

        }
    }

    private fun showAlertDialog(messageId: Int) {
        AlertDialog.Builder(this).setTitle(R.string.unknown_error).setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

}