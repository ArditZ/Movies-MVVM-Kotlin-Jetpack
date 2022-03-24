package com.android.movieapp.domain

import com.android.movieapp.data.model.Movie

data class MovieDetailsUi(
    val id: Int,
    val title: String,
    val posterPathUrl: String,
    val backdropPathUrl: String,
    val overview: String,
    val isFavorite: Boolean
)

fun Movie.toMovieDetailsUi(): MovieDetailsUi {
    return MovieDetailsUi(
        id = id ?: 0,
        title = title ?: "",
        posterPathUrl = posterPathUrl ?: "",
        backdropPathUrl = backdropPathUrl ?: "",
        overview = overview ?: "",
        isFavorite = false
    )
}
