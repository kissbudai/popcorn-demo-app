package com.demo.popcornapp.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.popcornapp.R
import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.usecase.GetMoviesForQueryUseCase
import com.demo.popcornapp.data.utils.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var testCoroutineDispatcher: TestCoroutineDispatcher

    private val getMoviesForQueryUseCase: GetMoviesForQueryUseCase = mock()

    private lateinit var sut: HomeViewModel

    @Before
    fun setup() {
        sut = HomeViewModel(getMoviesForQueryUseCase)
    }

    @Before
    fun setupDispatchers() {
        testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN empty search query WHEN performing a search THEN a warning event is emitted as empty search not allowed`() {
        sut.performSearch()

        Assert.assertEquals(HomeViewModel.VMEvent.ShowSearchQueryRule, sut.event.value?.peek())
    }

    @Test
    fun `GIVEN search query with only 1 character WHEN performing a search THEN a warning event is emitted as at least 2 characters are required for search`() {
        sut.searchQuery.value = "a"

        sut.performSearch()

        Assert.assertEquals(HomeViewModel.VMEvent.ShowSearchQueryRule, sut.event.value?.peek())
    }

    @Test
    fun `GIVEN valid search query WHEN performing the search THEN the loading is visible`() = runBlockingTest {
        sut.searchQuery.value = "abc"
        whenever(getMoviesForQueryUseCase.invoke(any())).doReturn(Result.Success(emptyList()))

        val mockObserver: Observer<Boolean> = mock()

        testCoroutineDispatcher.pauseDispatcher()
        sut.isLoading.observeForever(mockObserver)

        sut.performSearch()

        Assert.assertEquals(true, sut.isLoading.value)

        testCoroutineDispatcher.resumeDispatcher()
        sut.isLoading.removeObserver(mockObserver)
    }

    @Test
    fun `GIVEN an initialized view model WHEN no interaction was made with it THEN the loading is hidden`() = runBlockingTest {

        val mockObserver: Observer<Boolean> = mock()

        testCoroutineDispatcher.pauseDispatcher()
        sut.isLoading.observeForever(mockObserver)

        Assert.assertNull(sut.isLoading.value)

        testCoroutineDispatcher.resumeDispatcher()
        sut.isLoading.removeObserver(mockObserver)
    }

    @Test
    fun `GIVEN error response WHEN search is performed THEN a failure event is emitted`() = runBlocking {
        sut.searchQuery.value = "abc"

        whenever(getMoviesForQueryUseCase.invoke(any())).doReturn(Result.Error(Exception("")))

        sut.performSearch()

        Assert.assertEquals(HomeViewModel.VMEvent.SearchFailed(R.string.something_went_wrong), sut.event.value?.peek())
    }

    @Test
    fun `GIVEN success movie list response WHEN search is performed THEN a success event is emitted`() = runBlocking {
        sut.searchQuery.value = "abc"

        val movieList = listOf(
            Movie(
                id = 3279,
                title = "Movie title",
                originalTitle = "Original title",
                description = "Movie description",
                originalLanguage = "en",
                releaseDate = "2020-01-01",
                backdropPath = "",
                posterPath = "",
                isVideo = false,
                popularity = 200f,
                voteCount = 10,
                voteAvg = 5.3f,
                isAdult = false,
                genreIds = listOf(1, 2)
            )
        )

        whenever(getMoviesForQueryUseCase.invoke(any())).doReturn(Result.Success(movieList))

        sut.performSearch()

        Assert.assertEquals(HomeViewModel.VMEvent.SearchSucceeded(movieList), sut.event.value?.peek())
    }
}