package com.demo.popcornapp.feature.detail

import androidx.lifecycle.ViewModel
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.utils.DateHandler
import com.demo.popcornapp.shared.MovieTagBuilder

class MovieDetailViewModel(val movie: MovieUiModel, movieTagBuilder: MovieTagBuilder, dateHandler: DateHandler) : ViewModel() {

    val year: String = dateHandler.getYearFromMillis(dateHandler.parseFromYearMonthDay(movie.releaseDate)).toString()

    val tags: String = movieTagBuilder(movie)
}