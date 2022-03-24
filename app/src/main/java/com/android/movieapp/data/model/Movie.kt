package com.android.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("id")
    val id: Int?,
    val title: String?,
    @field:SerializedName("poster_path")
    val posterPathUrl: String?,
    @field:SerializedName("backdrop_path")
    val backdropPathUrl: String?,
    val overview: String?
)