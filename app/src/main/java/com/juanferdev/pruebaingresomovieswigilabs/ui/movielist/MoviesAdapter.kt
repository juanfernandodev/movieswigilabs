package com.juanferdev.pruebaingresomovieswigilabs.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.juanferdev.pruebaingresomovieswigilabs.Movie
import com.juanferdev.pruebaingresomovieswigilabs.R
import com.juanferdev.pruebaingresomovieswigilabs.databinding.ItemMovieBinding

class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.DogViewHolder>(DiffCallback) {

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
            binding.imageMovie.load(R.drawable.descarga)
            binding.titleMovie.text = movie.title
            binding.descriptionMovie.text = movie.overview.substringBefore('.')
            binding.containerItemMovie.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }
        }
    }

}