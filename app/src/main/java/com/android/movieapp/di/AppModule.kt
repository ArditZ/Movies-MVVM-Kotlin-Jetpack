package com.android.movieapp.di

import com.android.movieapp.data.api.MovieService
import com.android.movieapp.data.db.MoviesDao
import com.android.movieapp.data.movie.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideMovieDetailsRepository(
        service: MovieService,
        ioDispatcher: CoroutineDispatcher
    ): MovieDetailsRepository = MovieDetailsRepositoryImpl(service, ioDispatcher)

    @Provides
    fun provideFeedMovieRepository(
        dao: MoviesDao,
        service: MovieService,
        ioDispatcher: CoroutineDispatcher
    ): MoviesRepository = MoviesRepositoryImpl(dao, service, ioDispatcher)
}
