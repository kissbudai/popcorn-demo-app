package com.demo.popcornapp.feature.detail

import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.shared.MovieTagBuilder
import com.demo.popcornapp.utils.DateHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Test

class MovieDetailViewModelTest {

    private val movieTagBuilder: MovieTagBuilder = mock()
    private val dateHandler: DateHandler = mock()

    @Test
    fun `GIVEN the view model with a movie WHEN created THEN the tags are requested from the tag builder`() {
        val movie: MovieUiModel = mock()

        MovieDetailViewModel(movie, movieTagBuilder, dateHandler)

        verify(movieTagBuilder, times(1)).invoke(movie)
    }

    @Test
    fun `GIVEN the view model with a movie WHEN created THEN the year is calculated for the movie`() {
        val movie: MovieUiModel = mock()
        whenever(dateHandler.getYearFromMillis(any())).doReturn(2020)

        val sut = MovieDetailViewModel(movie, movieTagBuilder, dateHandler)

        Assert.assertEquals(sut.year, "2020")
    }
}