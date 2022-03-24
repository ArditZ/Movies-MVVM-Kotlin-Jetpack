package com.android.movieapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MovieServiceTest {
    private lateinit var service: MovieService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMovies() = runBlocking {
        enqueueResponse("top-rated-movies.json")
        val movies = service.getMovieList("key", 1).results

        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/movie/top_rated?api_key=key&page=1")
        assertThat(movies.size).isEqualTo(3)
        assertThat(movies[0].title).isEqualTo("Cruella")
        assertThat(movies[0].id).isEqualTo(337404)
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
            .source()
            .buffer()

        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(inputStream.readString(Charsets.UTF_8))
        )
    }
}