package com.android.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.db.MovieEntity
import com.android.movieapp.data.movie.MoviesRepository
import com.android.movieapp.domain.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedMovieViewModel @Inject constructor(private val repository: MoviesRepository) :
    ViewModel() {
    private val _movieList = MutableSharedFlow<List<MovieUi>>(replay = 1)
    private val _error = MutableStateFlow<String?>(null)
    private val _isFavoriteClicked = MutableStateFlow<Boolean?>(null)

    val isFavoriteClicked: StateFlow<Boolean?> = _isFavoriteClicked
    val movieList: SharedFlow<List<MovieUi>> = _movieList
    val error: StateFlow<String?> = _error

    init {
        getMovieList()
        getFavoriteProducts()
    }

    private val favoriteMovies = MutableStateFlow<List<MovieEntity>>(emptyList())

    private fun getFavoriteProducts() = viewModelScope.launch {
        repository.getFavoriteMovies().collect {
            favoriteMovies.value = it
        }
    }

    fun getMovieList() {
        viewModelScope.launch {
            repository.getFeedMovieList().combine(favoriteMovies) { result, favorites ->
                when (result) {
                    is Result.Success -> {
                        result.data.applyFavorites(favorites)
                    }
                    is Result.Error -> {
                        _error.value = result.exception.message
                        listOf()
                    }
                }
            }.collect { result ->
                _movieList.emit(result)
            }
        }
    }

    fun setItemFavorite(item: MovieUi) = viewModelScope.launch {
        item.let {
            if (item.isFavorite) {
                repository.removeFavoriteMovie(it.id)
                _isFavoriteClicked.value = false
            } else {
                repository.setFavoriteMovie(it)
                _isFavoriteClicked.value = true
            }
        }
    }

    fun onErrorShown() {
        _error.value = null
    }

    fun onToastShown() {
        _isFavoriteClicked.value = null
    }

    private fun List<MovieUi>.applyFavorites(
        favoriteList: List<MovieEntity>
    ): List<MovieUi> {
        forEach { result ->
            val index = favoriteList.binarySearchBy(result.id) {
                it.movieId
            }
            result.isFavorite = index >= 0
        }
        return this
    }
}