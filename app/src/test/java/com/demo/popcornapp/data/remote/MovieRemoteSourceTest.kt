package com.demo.popcornapp.data.remote

import com.demo.popcornapp.createDataTestModule
import com.demo.popcornapp.data.model.Movie
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MovieRemoteSourceTest : KoinTest {

    private lateinit var mockWebServer: MockWebServer

    private val sut: MovieRemoteSource by inject()

    @Before
    fun initMockServer() {
        mockWebServer = MockWebServer()

        startKoin {
            modules(createDataTestModule(mockWebServer.url("").toString()))
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @After
    fun closeKoin() {
        stopKoin()
    }

    @Test
    fun `GIVEN a movie search request WHEN performed THEN a get request is made`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(SUCCESS_RESPONSE))

        sut.getMoviesForQuery("testQuery")

        val request = mockWebServer.takeRequest()

        Assert.assertEquals("GET", request.method)
    }

    @Test
    fun `GIVEN a movie search request WHEN performed THEN the search query and API key is passed as query`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(SUCCESS_RESPONSE))

        sut.getMoviesForQuery("testQuery")

        val request = mockWebServer.takeRequest()

        Assert.assertEquals("testQuery", request.requestUrl?.queryParameter("query"))
        Assert.assertEquals("2696829a81b1b5827d515ff121700838", request.requestUrl?.queryParameter("api_key"))
    }

    @Test
    fun `GIVEN response with result WHEN the api call is performed THEN the parsed list of movies is returned`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(SUCCESS_RESPONSE))

        val movieList = sut.getMoviesForQuery("testQuery")

        val expectedList = listOf(
            Movie(
                id = 544812,
                title = "The Third Wife",
                originalTitle = "Người Vợ Ba",
                description = "Though only 14 years old, May is selected to be the third wife of a wealthy landowner. Her new home seems idyllic, her husband favours her, and she quickly becomes pregnant with what she is certain will be the desired male progeny. But trouble is quietly brewing: she witnesses a forbidden tryst that will spark a chain reaction of misfortunes — and stir in May urges that until now had been dormant.",
                originalLanguage = "vi",
                releaseDate = "2019-01-11",
                backdropPath = "/um7tqx0ue4bJUPNKv6zeVgJU97g.jpg",
                posterPath = "/zWOoLDBwnngREgQcRBKlEiKDgEF.jpg",
                isVideo = false,
                popularity = 5.092f,
                voteCount = 27,
                voteAvg = 7f,
                isAdult = false,
                genreIds = listOf(18)
            )
        )

        Assert.assertEquals(expectedList, movieList)
    }

    @Test(expected = JsonDataException::class)
    fun `GIVEN an invalid response body WHEN a search request is made THEN a json exception is thrown`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(MISSING_RESULT_LIST_RESPONSE))

        val result = sut.getMoviesForQuery("testQuery")
    }

    companion object {
        private const val SUCCESS_RESPONSE = "{\n" +
                "  \"page\": 1,\n" +
                "  \"total_results\": 10000,\n" +
                "  \"total_pages\": 500,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"popularity\": 5.092,\n" +
                "      \"vote_count\": 27,\n" +
                "      \"video\": false,\n" +
                "      \"poster_path\": \"\\/zWOoLDBwnngREgQcRBKlEiKDgEF.jpg\",\n" +
                "      \"id\": 544812,\n" +
                "      \"adult\": false,\n" +
                "      \"backdrop_path\": \"\\/um7tqx0ue4bJUPNKv6zeVgJU97g.jpg\",\n" +
                "      \"original_language\": \"vi\",\n" +
                "      \"original_title\": \"Người Vợ Ba\",\n" +
                "      \"genre_ids\": [\n" +
                "        18\n" +
                "      ],\n" +
                "      \"title\": \"The Third Wife\",\n" +
                "      \"vote_average\": 7,\n" +
                "      \"overview\": \"Though only 14 years old, May is selected to be the third wife of a wealthy landowner. Her new home seems idyllic, her husband favours her, and she quickly becomes pregnant with what she is certain will be the desired male progeny. But trouble is quietly brewing: she witnesses a forbidden tryst that will spark a chain reaction of misfortunes — and stir in May urges that until now had been dormant.\",\n" +
                "      \"release_date\": \"2019-01-11\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        private const val MISSING_RESULT_LIST_RESPONSE = "{\n" +
                "  \"page\": 1,\n" +
                "  \"total_results\": 10000,\n" +
                "  \"total_pages\": 500\n" +
                "}"
    }
}