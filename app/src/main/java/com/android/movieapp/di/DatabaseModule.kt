package com.android.movieapp.di

import android.app.Application
import com.android.movieapp.data.db.AppDatabase
import com.android.movieapp.data.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.buildDatabase(app)

    @Provides
    fun provideMoviesDao(db: AppDatabase): MoviesDao {
        return db.moviesDao()
    }
}