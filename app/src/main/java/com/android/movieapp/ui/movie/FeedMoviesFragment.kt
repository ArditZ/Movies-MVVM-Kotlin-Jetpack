package com.android.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.android.movieapp.R
import com.android.movieapp.data.db.MovieEntity
import com.android.movieapp.databinding.FragmentFeedMoviesBinding
import com.android.movieapp.domain.MovieUi
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedMoviesFragment : Fragment() {

    private lateinit var binding: FragmentFeedMoviesBinding
    private val feedMovieViewModel: FeedMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    feedMovieViewModel.movieList.collect {
                        showFeedMovies(binding.feedMovieRecycler, it)
                    }
                }

                launch {
                    feedMovieViewModel.error.collect {
                        it?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            feedMovieViewModel.onErrorShown()
                        }
                    }
                }
                launch {
                    feedMovieViewModel.isFavoriteClicked.collect {
                        it?.let {
                            val message: String =
                                if (it) getString(R.string.add_favorite) else getString(R.string.remove_favorite)
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            feedMovieViewModel.onToastShown()
                        }
                    }
                }
            }
        }
    }

    private fun showFeedMovies(recyclerView: RecyclerView, list: List<MovieUi>) {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = FeedMovieAdapter(feedMovieViewModel, viewLifecycleOwner)
        }
        (recyclerView.adapter as FeedMovieAdapter).submitList(list)
    }
}