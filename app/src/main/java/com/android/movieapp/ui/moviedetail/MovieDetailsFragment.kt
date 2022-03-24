package com.android.movieapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.android.movieapp.databinding.FragmentMovieDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var view: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        movieDetailsViewModel.getMovieDetails(args.movieId)
        initObservers()

        return view.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    movieDetailsViewModel.movie.collect {
                        if (it == null) return@collect
                        view.feedMovieDetails = it
                        view.apply {
                            feedMovieDetails = it
                            executePendingBindings()
                        }
                    }
                }
                launch {
                    movieDetailsViewModel.error.collect {
                        it?.let {
                            Snackbar.make(view.root, it, Snackbar.LENGTH_LONG).show()
                            movieDetailsViewModel.onErrorShown()
                        }
                    }
                }
            }
        }
    }
}