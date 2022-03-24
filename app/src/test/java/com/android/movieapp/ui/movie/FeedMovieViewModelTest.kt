@file:OptIn(ExperimentalCoroutinesApi::class)

package com.android.movieapp.ui.movie

import com.android.movieapp.MainCoroutineRule
import com.android.movieapp.TestData
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.movie.MoviesRepository
import com.android.movieapp.runBlockingTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FeedMovieViewModelTest {
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: FeedMovieViewModel
    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = mock(MoviesRepository::class.java)
        viewModel = FeedMovieViewModel(repository)
    }

    @Test
    fun getMovieList() = coroutineRule.runBlockingTest {
        `when`(repository.getFeedMovieList()).thenReturn(
            flowOf(Result.Success(listOf(TestData.movie1Ui)))
        )
        viewModel.getMovieList()

        Truth.assertThat(viewModel.movieList.first()).isEqualTo(
            TestData.movieList
        )
    }

    @Test
    fun getMovieList_throwException() = coroutineRule.runBlockingTest {
        `when`(repository.getFeedMovieList()).thenReturn(
            flowOf(Result.Error(RuntimeException("No data")))
        )
        viewModel.getMovieList()

        Truth.assertThat(viewModel.error.first()).isEqualTo(
            "No data"
        )
    }
}