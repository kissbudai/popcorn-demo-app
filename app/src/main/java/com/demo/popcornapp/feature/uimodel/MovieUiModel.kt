package com.demo.popcornapp.feature.uimodel

import android.os.Parcelable
import com.demo.popcornapp.data.model.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUiModel(
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
) : Parcelable

fun Movie.toUiModel() = MovieUiModel(
    id = id,
    title = title,
    originalTitle = originalTitle,
    description = description,
    originalLanguage = originalLanguage,
    releaseDate = releaseDate,
    backdropPath = backdropPath,
    posterPath = posterPath,
    isVideo = isVideo,
    popularity = popularity,
    voteCount = voteCount,
    voteAvg = voteAvg,
    isAdult = isAdult,
    genreIds = genreIds
)