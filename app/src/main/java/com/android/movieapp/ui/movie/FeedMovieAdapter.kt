package com.android.movieapp.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.movieapp.binding.bindFavoriteIcon
import com.android.movieapp.databinding.ListItemMovieBinding
import com.android.movieapp.domain.MovieUi

class FeedMovieAdapter(
    private val viewModel: FeedMovieViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) :
    ListAdapter<MovieUi, FeedMovieAdapter.FeedMovieViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedMovieViewHolder {
        return FeedMovieViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedMovieViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            lifecycleOwner = viewLifecycleOwner
            onItemClickListener = createOnClickListener(item.id)
            onFavoriteClickListener = onFavoriteClick(holder.binding.favoriteMovie, item)
            movie = item
            executePendingBindings()
        }
    }

    class FeedMovieViewHolder(val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun createOnClickListener(id: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                FeedMoviesFragmentDirections.actionFeedMoviesFragmentToMovieDetailsFragment(id)
            it.findNavController().navigate(direction)
        }
    }

    private fun onFavoriteClick(favoriteIcon: ImageView, movie: MovieUi): View.OnClickListener {
        return View.OnClickListener {
            viewModel.setItemFavorite(movie)
            // DiffUtil doesn't update recyclerview item onFavoriteClick
            movie.isFavorite = movie.isFavorite.not()
            bindFavoriteIcon(favoriteIcon, movie.isFavorite)
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<MovieUi>() {

    override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem == newItem
    }
}