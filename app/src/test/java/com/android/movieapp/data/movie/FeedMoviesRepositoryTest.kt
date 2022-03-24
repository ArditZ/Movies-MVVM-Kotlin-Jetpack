package com.android.movieapp.data.movie

import com.android.movieapp.BuildConfig.API_KEY
import com.android.movieapp.TestData
import com.android.movieapp.data.api.FeedMoviesResponse
import com.android.movieapp.data.api.MovieService
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.db.MoviesDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FeedMoviesRepositoryTest {
    private lateinit var dao: MoviesDao
    private lateinit var service: MovieService
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun init() {
        dao = mock(MoviesDao::class.java)
        service = mock(MovieService::class.java)
        repository = MoviesRepositoryImpl(dao, service, Dispatchers.IO)
    }

    @Test
    fun getFeedMovies_success() = runTest {
        `when`(service.getMovieList(API_KEY, 1))
            .thenReturn(FeedMoviesResponse(TestData.movieList))

        val movies = repository.getFeedMovieList().first()
        assertThat(movies is Result.Success)
        if (movies is Result.Success) {
            assertThat(movies.data).isEqualTo(TestData.movieListUi)
        }
    }

    @Test
    fun getFeedMovies_error() = runTest {
        `when`(service.getMovieList(API_KEY, 1)).thenThrow(
            RuntimeException("Runtime exception")
        )

        val movieList = repository.getFeedMovieList().first()
        assertThat(movieList is Result.Error)
        if (movieList is Result.Error) {
            assertThat(movieList.exception.message).isEqualTo("Runtime exception")
        }
    }
}