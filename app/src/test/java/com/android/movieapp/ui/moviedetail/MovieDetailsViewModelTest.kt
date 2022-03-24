package com.android.movieapp.ui.moviedetail

import com.android.movieapp.MainCoroutineRule
import com.android.movieapp.TestData
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.movie.MovieDetailsRepository
import com.android.movieapp.runBlockingTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var repository: MovieDetailsRepository

    @Before
    fun setUp() {
        repository = mock(MovieDetailsRepository::class.java)
        movieDetailsViewModel = MovieDetailsViewModel(repository)
    }

    @Test
    fun getMovieDetails() = coroutineRule.runBlockingTest {
        `when`(repository.getMovieDetails(1)).thenReturn(
            flowOf(Result.Success(TestData.movieDetailsUi))
        )
        movieDetailsViewModel.getMovieDetails(1)

        assertThat(movieDetailsViewModel.movie.first()).isEqualTo(TestData.movie1)
    }

    @Test
    fun getMovieDetails_throwException() = coroutineRule.runBlockingTest {
        `when`(repository.getMovieDetails(2)).thenReturn(
            flowOf(Result.Error(Exception("No data")))
        )
        movieDetailsViewModel.getMovieDetails(2)

        assertThat(movieDetailsViewModel.error.first()).isEqualTo(
            "No data"
        )
    }
}
