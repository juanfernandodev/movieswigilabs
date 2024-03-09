package com.juanferdev.pruebaingresomovieswigilabs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val popularity: Double,
    val releaseDate: String,
    val isFavorite: Boolean
) : Parcelable
