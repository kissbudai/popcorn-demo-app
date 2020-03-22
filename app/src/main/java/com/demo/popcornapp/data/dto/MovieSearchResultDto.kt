package com.demo.popcornapp.data.dto

import com.demo.popcornapp.data.dto.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieSearchResultDto(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val pages: Int,
    @field:Json(name = "results") val results: List<MovieDto>
)