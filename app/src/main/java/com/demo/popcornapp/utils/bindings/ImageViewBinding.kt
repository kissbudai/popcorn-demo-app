package com.demo.popcornapp.utils.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.demo.popcornapp.R

@BindingAdapter("moviePoster")
fun ImageView.setMoviePoster(poster: String) {
    Glide.with(this)
        .load("https://image.tmdb.org/t/p/w92/$poster")
        .placeholder(R.drawable.ic_app_logo)
        .into(this)
}