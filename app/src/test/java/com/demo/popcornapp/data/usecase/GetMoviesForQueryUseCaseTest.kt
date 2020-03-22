package com.demo.popcornapp.data.usecase

import android.util.MalformedJsonException
import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.remote.MovieRemoteSource
import com.demo.popcornapp.data.utils.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMoviesForQueryUseCaseTest {

    private val movieRemoteSource: MovieRemoteSource = mock()

    private lateinit var sut: GetMoviesForQueryUseCase

    @Before
    fun setup() {
        sut = GetMoviesForQueryUseCase(movieRemoteSource)
    }

    @Test
    fun `GIVEN a movie list remote response WHEN requesting the search data THEN the movie list is wrapped into a Success result wrapper`() = runBlocking {
        whenever(movieRemoteSource.getMoviesForQuery(any())).thenReturn(listOf())

        val response = sut.invoke("testQuery")

        Assert.assertEquals(Result.Success<List<Movie>>(listOf()), response)
    }

    @Test
    fun `GIVEN exception from remote WHEN requesting the search data THEN the exception is returned, wrapped in Error result wrapper`() = runBlocking {
        whenever(movieRemoteSource.getMoviesForQuery(any())).thenThrow(Exception(""))

        val response = sut.invoke("testQuery")

        Assert.assertTrue(response is Result.Error)
    }
}