package com.android.movieapp.data.movie

import com.android.movieapp.BuildConfig.API_KEY
import com.android.movieapp.TestData
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsRepositoryTest {
    private lateinit var repository: MovieDetailsRepositoryImpl
    private lateinit var service: MovieService
    private lateinit var dao: MoviesDao

    @Before
    fun setup() {
        service = mock(MovieService::class.java)
        dao = mock(MoviesDao::class.java)
        repository = MovieDetailsRepositoryImpl(service, Dispatchers.Unconfined)
    }

    @Test
    fun getMovieDetails_success() = runTest {
        `when`(service.getMovieDetails(1, API_KEY)).thenReturn(
            TestData.movie1
        )
        val movie = repository.getMovieDetails(1).first()
        assertThat(movie is Result.Success)
        if (movie is Result.Success) {
            assertThat(movie.data).isEqualTo(TestData.movie1)
        }
    }

    @Test
    fun getMovieDetails_error() = runTest {
        `when`(service.getMovieDetails(1, API_KEY)).thenThrow(
            RuntimeException("Runtime exception")
        )

        val movie = repository.getMovieDetails(1).first()
        assertThat(movie is Result.Error)
        if (movie is Result.Error) {
            assertThat(movie.exception.message).isEqualTo("Runtime exception")
        }
    }
}