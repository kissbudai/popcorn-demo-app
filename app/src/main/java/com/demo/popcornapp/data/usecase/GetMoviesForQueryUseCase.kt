package com.demo.popcornapp.data.usecase

import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.remote.MovieRemoteSource
import com.demo.popcornapp.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case to perform a search action.
 */
class GetMoviesForQueryUseCase(private val movieRemoteSource: MovieRemoteSource) {

    suspend operator fun invoke(query: String): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            Result.invoke { movieRemoteSource.getMoviesForQuery(query) }
        }
}