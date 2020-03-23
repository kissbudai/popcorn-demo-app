package com.demo.popcornapp.shared

import com.demo.popcornapp.R
import com.demo.popcornapp.data.model.Genre
import com.demo.popcornapp.data.usecase.GetGenreListUseCase
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.utils.Clock
import com.demo.popcornapp.utils.DateHandler
import com.demo.popcornapp.utils.StringLookup

/**
 * Use case for building the tag list for a [MovieUiModel].
 */
class MovieTagBuilder(
    private val getGenreList: GetGenreListUseCase,
    private val stringLookup: StringLookup,
    private val dateHandler: DateHandler,
    private val clock: Clock
) {

    operator fun invoke(movie: MovieUiModel): String {
        val tags = mutableListOf<String>()

        tags.add(
            getGenreList()
                .filter { movie.genreIds.contains(it.id) }
                .map(Genre::name)
                .joinToString(", ")
        )

        if (movie.isAdult) {
            tags.add(stringLookup.getString(R.string.adult))
        }

        if (movie.popularity > 30.0f) {
            tags.add(stringLookup.getString(R.string.popular))
        }

        if (movie.isVideo) {
            tags.add(stringLookup.getString(R.string.video))
        }

        val thisYear = dateHandler.getYearFromMillis(clock.currentTime())
        val releaseYear = dateHandler.getYearFromMillis(dateHandler.parseFromYearMonthDay(movie.releaseDate))
        if (thisYear == releaseYear) {
            tags.add(stringLookup.getString(R.string.new_release))
        }

        tags.add("${stringLookup.getString(R.string.language)}: ${movie.originalLanguage}")

        return tags.joinToString(separator = " | ")
    }
}