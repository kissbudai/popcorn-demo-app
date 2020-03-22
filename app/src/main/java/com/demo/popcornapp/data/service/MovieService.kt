package com.demo.popcornapp.data.service

import com.demo.popcornapp.data.dto.MovieSearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @Throws(Throwable::class)
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") searchQuery: String,
        @Query("api_key") apiKey: String = "2696829a81b1b5827d515ff121700838"
    ): MovieSearchResultDto
}