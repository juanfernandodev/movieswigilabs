package com.juanferdev.pruebaingresomovieswigilabs.api

import com.juanferdev.pruebaingresomovieswigilabs.api.response.MovieListApiResponse
import retrofit2.http.GET

const val BASE_URL = "https://api.themoviedb.org/3/"
private const val GET_ALL_MOVIES_URL =
    "movie/popular?api_key=09963e300150f9831c46a1828a82a984&language=en-US"

fun interface ApiService {
    @GET(GET_ALL_MOVIES_URL)
    suspend fun getAllMovies(): MovieListApiResponse

}