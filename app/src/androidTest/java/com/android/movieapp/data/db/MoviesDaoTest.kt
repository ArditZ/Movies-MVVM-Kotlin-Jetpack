package com.android.movieapp.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.movieapp.data.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MoviesDaoTest : AppDatabaseTest() {
    private lateinit var moviesDao: MoviesDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        moviesDao = db.moviesDao()
    }

    @Test
    fun insertFavoriteMovie() = runTest {
        moviesDao.insertFavoriteProduct(TestData.movieEntity1)

        assertThat(moviesDao.isMovieFavorite(TestData.movieEntity1.movieId)).isTrue()
        db.clearAllTables()
    }

    @Test
    fun deleteFavoriteMovie() = runTest {
        moviesDao.insertFavoriteProduct(TestData.movieEntity1)

        moviesDao.deleteFavoriteProduct(TestData.movieEntity1.movieId)

        assertThat(moviesDao.getFavoriteMovies().first()).isEmpty()
    }

    @Test
    fun getFavoriteProducts() = runTest {
        moviesDao.insertFavoriteProduct(TestData.movieEntity1)
        moviesDao.insertFavoriteProduct(TestData.movieEntity2)

        val list = moviesDao.getFavoriteMovies().first()

        assertThat(list).isEqualTo(
            listOf(TestData.movieEntity1, TestData.movieEntity2)
        )
        db.clearAllTables()
    }

    @Test
    fun isProductFavorite() = runTest {
        moviesDao.insertFavoriteProduct(TestData.movieEntity1)

        assertThat(moviesDao.isMovieFavorite(TestData.movieEntity1.movieId)).isTrue()
    }
}