package com.android.movieapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favoriteMovies")
data class MovieEntity(
    @PrimaryKey
    val movieId: Int,
    val title: String,
    val posterPathUrl: String
)






