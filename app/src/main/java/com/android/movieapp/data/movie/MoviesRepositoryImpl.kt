package com.android.movieapp.data.movie

import com.android.movieapp.BuildConfig
import com.android.movieapp.data.api.MovieService
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.db.MovieEntity
import com.android.movieapp.data.db.MoviesDao
import com.android.movieapp.domain.MovieUi
import com.android.movieapp.domain.toUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MoviesRepository {
    fun getFeedMovieList(): Flow<Result<List<MovieUi>>>

    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    suspend fun setFavoriteMovie(movie: MovieUi)

    suspend fun removeFavoriteMovie(movieId: Int)
}

class MoviesRepositoryImpl @Inject constructor(
    private val dao: MoviesDao,
    private val movieService: MovieService,
    private val provideIoDispatcher: CoroutineDispatcher
) : MoviesRepository {
    override fun getFeedMovieList() = flow {
        try {
            val movies = movieService.getMovieList(BuildConfig.API_KEY, 1).results
            emit(Result.Success(movies.map { it.toUI() }))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(provideIoDispatcher)

    override fun getFavoriteMovies() = dao.getFavoriteMovies()

    override suspend fun setFavoriteMovie(movie: MovieUi) {
        val entity = MovieEntity(
            movieId = movie.id,
            title = movie.title,
            posterPathUrl = movie.posterPathUrl
        )
        dao.insertFavoriteProduct(entity)
    }

    override suspend fun removeFavoriteMovie(movieId: Int) {
        dao.deleteFavoriteProduct(movieId)
    }
}