package com.demo.popcornapp.data.model

data class Movie(
    val id: Int,

    val title: String,
    val originalTitle: String? = null,
    val description: String,
    val originalLanguage: String,
    val releaseDate: String,

    val backdropPath: String,
    val posterPath: String,

    val isVideo: Boolean,
    val popularity: Float,
    val voteCount: Int,
    val voteAvg: Float,

    val isAdult: Boolean,
    val genreIds: List<Int>
)