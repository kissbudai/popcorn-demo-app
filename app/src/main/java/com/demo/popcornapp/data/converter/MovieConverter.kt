package com.demo.popcornapp.data.converter

import com.demo.popcornapp.data.dto.MovieDto
import com.demo.popcornapp.data.model.Movie

fun MovieDto.toModel() = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    description = description.orEmpty(),
    originalLanguage = originalLanguage.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    posterPath = posterPath.orEmpty(),
    isVideo = isVideo,
    popularity = popularity,
    voteCount = voteCount,
    voteAvg = voteAvg,
    isAdult = isAdult,
    genreIds = genreIds
)