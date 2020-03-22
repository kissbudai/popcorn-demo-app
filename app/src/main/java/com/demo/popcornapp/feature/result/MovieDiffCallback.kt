package com.demo.popcornapp.feature.result

import androidx.recyclerview.widget.DiffUtil

class MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean =
        oldItem == newItem
}