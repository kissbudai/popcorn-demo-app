package com.demo.popcornapp.feature.result

import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.shared.MovieTagBuilder
import com.demo.popcornapp.utils.DateHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieResultViewModelTest {

    private val movieTagBuilder: MovieTagBuilder = mock()
    private val dateHandler = DateHandler()

    private lateinit var sut: MovieResultViewModel

    @Before
    fun setup() {
        sut = MovieResultViewModel("testSearch", MOVIES, movieTagBuilder, dateHandler)
    }

    @Test
    fun `GIVEN the view model with non-empty movie list WHEN initialized THEN the movie list is mapped to list model`() {

        whenever(movieTagBuilder.invoke(any())).doReturn("tags")

        val expected = listOf(
            MovieListItem(
                id = 1,
                poster = "",
                title = "Movie 1",
                year = "2020",
                rating = 1f,
                ratingCount = 1,
                tags = "tags"
            ),
            MovieListItem(
                id = 2,
                poster = "",
                title = "Movie 2",
                year = "2020",
                rating = 2f,
                ratingCount = 2,
                tags = "tags"
            )
        )

        Assert.assertEquals(expected, sut.movieList.value)
    }

    @Test
    fun `GIVEN the viewModel with empty movie list WHEN initialized THEN an empty list model list is exposed`() {
        val sut = MovieResultViewModel("testSearch", emptyList(), movieTagBuilder, dateHandler)

        Assert.assertEquals(emptyList<MovieListItem>(), sut.movieList.value)
    }

    @Test
    fun `GIVEN the viewModel with movie list WHEN a movie is requested for index larger the list size THEN null is returned`() {
        val movie = sut.getMovie(10)

        Assert.assertNull(movie)
    }

    @Test
    fun `GIVEN the viewModel with movie list WHEN a movie is requested for valid index THEN the movie is returned`() {
        val movie = sut.getMovie(0)

        Assert.assertEquals(MOVIES[0], movie)
    }

    companion object {

        private val MOVIES = listOf(
            MovieUiModel(
                id = 1,
                title = "Movie 1",
                originalTitle = "Original 1",
                description = "Movie description 1",
                originalLanguage = "en",
                releaseDate = "2020-01-01",
                backdropPath = "",
                posterPath = "",
                isVideo = false,
                popularity = 1f,
                voteCount = 1,
                voteAvg = 1f,
                isAdult = false,
                genreIds = listOf(1)
            ),
            MovieUiModel(
                id = 2,
                title = "Movie 2",
                originalTitle = "Original 2",
                description = "Movie description 2",
                originalLanguage = "en",
                releaseDate = "2020-01-02",
                backdropPath = "",
                posterPath = "",
                isVideo = false,
                popularity = 2f,
                voteCount = 2,
                voteAvg = 2f,
                isAdult = false,
                genreIds = listOf(2)
            )
        )
    }
}