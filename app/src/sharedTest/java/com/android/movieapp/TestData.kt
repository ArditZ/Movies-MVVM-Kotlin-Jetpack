package com.android.movieapp

import com.android.movieapp.data.model.Movie
import com.android.movieapp.domain.MovieDetailsUi
import com.android.movieapp.domain.MovieUi

object TestData {
    val movie1 = Movie(1, "foo", "bar", "test", "descr")
    val movie2 = Movie(2, "foo 2", "bar 2", "test 2", "descr 2")
    val movie1Ui = MovieUi(1, "foo", "bar", false)
    val movie2Ui = MovieUi(2, "foo 2", "bar 2", false)
    val movieDetailsUi = MovieDetailsUi(1, "foo", "bar", "test", "descr", false)

    val movieList = listOf(movie1, movie2)
    val movieListUi = listOf(movie1Ui, movie2Ui)
}