package com.juanferdev.pruebaingresomovieswigilabs.features.movielist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.core.models.Movie
import com.juanferdev.pruebaingresomovieswigilabs.core.network.IMAGES_BASE_URL
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ItemMovieBinding

class MoviesAdapter(private val context: Context) :
    ListAdapter<Movie, MoviesAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((Movie) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    private var onFavoriteClickListener: ((Movie) -> Unit)? = null

    fun setOnFavoriteClickListener(onFavoriteClickListener: ((Movie) -> Unit)) {
        this.onFavoriteClickListener = onFavoriteClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)
    }

    inner class DogViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.imageMovie.load(IMAGES_BASE_URL.plus(movie.posterPath))
            binding.buttonFavorite.background = if (movie.isFavorite) {
                AppCompatResources.getDrawable(context, R.drawable.baseline_favorite)
            } else {
                AppCompatResources.getDrawable(context, R.drawable.baseline_no_favorite)
            }
            binding.containerItemMovie.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }
            binding.buttonFavorite.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                onFavoriteClickListener?.invoke(movie)
            }
        }
    }

}