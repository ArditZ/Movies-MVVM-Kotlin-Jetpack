package com.android.movieapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.movieapp.BuildConfig
import com.android.movieapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(BuildConfig.BASE_IMAGE_URL + imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("bindFavorite")
fun bindFavoriteIcon(view: ImageView, isFavorite: Boolean) {
    if (isFavorite) {
        view.setImageResource(R.drawable.outline_favorite_24)
    } else {
        view.setImageResource(R.drawable.outline_favorite_border_24)
    }
}