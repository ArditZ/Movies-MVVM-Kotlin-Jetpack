package com.android.movieapp.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieapp.data.api.Result
import com.android.movieapp.data.movie.MovieDetailsRepository
import com.android.movieapp.domain.MovieDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    ViewModel() {
    private val _movie = MutableStateFlow<MovieDetailsUi?>(null)
    private val _error = MutableStateFlow<String?>(null)

    val movie: StateFlow<MovieDetailsUi?>
        get() = _movie
    val error: StateFlow<String?>
        get() = _error

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        repository.getMovieDetails(movieId).collect { result ->
            if (result is Result.Success) {
                _movie.value = result.data
            } else if (result is Result.Error) {
                _error.value = result.exception.message
            }
        }
    }

    fun onErrorShown() {
        _error.value = null
    }
}