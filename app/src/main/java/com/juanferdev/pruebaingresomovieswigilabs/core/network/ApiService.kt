package com.juanferdev.pruebaingresomovieswigilabs.core.network

import com.juanferdev.pruebaingresomovieswigilabs.core.network.response.MovieListApiResponse
import retrofit2.http.GET

const val BASE_URL = "https://api.themoviedb.org/3/"
private const val GET_ALL_MOVIES_URL =
    "movie/popular?api_key=09963e300150f9831c46a1828a82a984&language=en-US"
const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun interface ApiService {
    @GET(GET_ALL_MOVIES_URL)
    suspend fun getAllMovies(): MovieListApiResponse

}