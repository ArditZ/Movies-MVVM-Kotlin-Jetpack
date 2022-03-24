package com.android.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT EXISTS(SELECT 1 FROM favoriteMovies WHERE movieId =:moveId)")
    suspend fun isMovieFavorite(moveId: Int): Boolean

    @Query("SELECT * FROM favoriteMovies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(entity: MovieEntity)

    @Query("DELETE FROM favoriteMovies WHERE movieId=:id")
    suspend fun deleteFavoriteProduct(id: Int)
}