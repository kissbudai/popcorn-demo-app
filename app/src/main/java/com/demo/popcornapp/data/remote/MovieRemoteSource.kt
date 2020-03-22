package com.demo.popcornapp.data.remote

import com.demo.popcornapp.data.converter.toModel
import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.service.MovieService

class MovieRemoteSource(private val movieService: MovieService) {

    @Throws(Throwable::class)
    suspend fun getMoviesForQuery(query: String): List<Movie> =
        movieService.getMovies(searchQuery = query).results.map { it.toModel() }
}