package com.demo.popcornapp.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDto(
    @field:Json(name = "id") val id: Int,

    @field:Json(name = "title") val title: String,
    @field:Json(name = "original_title") val originalTitle: String?,
    @field:Json(name = "overview") val description: String?,
    @field:Json(name = "original_language") val originalLanguage: String?,
    @field:Json(name = "release_date") val releaseDate: String?,

    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "poster_path") val posterPath: String?,

    @field:Json(name = "video") val isVideo: Boolean,
    @field:Json(name = "popularity") val popularity: Float,
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "vote_average") val voteAvg: Float,

    @field:Json(name = "adult") val isAdult: Boolean,
    @field:Json(name = "genre_ids") val genreIds: List<Int>
)