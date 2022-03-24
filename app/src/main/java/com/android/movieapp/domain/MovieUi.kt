package com.android.movieapp.domain

import com.android.movieapp.data.model.Movie

data class MovieUi(
    val id: Int,
    val title: String,
    val posterPathUrl: String,
    var isFavorite: Boolean
)

fun Movie.toUI(): MovieUi {
    return MovieUi(
        id = id ?: 0,
        title = title ?: "",
        posterPathUrl = posterPathUrl ?: "",
        isFavorite = false
    )
}