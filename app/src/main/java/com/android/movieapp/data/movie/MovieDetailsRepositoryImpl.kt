package com.android.movieapp.data.movie

import com.android.movieapp.BuildConfig
import com.android.movieapp.data.api.MovieService
import com.android.movieapp.data.api.Result
import com.android.movieapp.domain.MovieDetailsUi
import com.android.movieapp.domain.toMovieDetailsUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Flow<Result<MovieDetailsUi>>
}

class MovieDetailsRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val provideIoDispatcher: CoroutineDispatcher
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int) = flow {
        try {
            val result = service.getMovieDetails(movieId, BuildConfig.API_KEY)
            emit(Result.Success(result.toMovieDetailsUi()))
        } catch (e: Exception) {
            emit(Result.Error(Exception(e.message)))
        }
    }.flowOn(provideIoDispatcher)
}