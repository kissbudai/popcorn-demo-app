package com.demo.popcornapp.feature.result

/**
 * Ui model used for the movie list on [MovieResultFragment].
 */
data class MovieListItem(
    val id: Int,
    val poster: String,
    val title: String,
    val year: String,
    val rating: Float,
    val ratingCount: Int,
    val tags: String
)