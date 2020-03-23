package com.demo.popcornapp.feature.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.utils.DateHandler
import com.demo.popcornapp.shared.MovieTagBuilder

class MovieResultViewModel(
    val searchQuery: String,
    private val movies: List<MovieUiModel>,
    private val movieTagBuilder: MovieTagBuilder,
    private val dateHandler: DateHandler
) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieListItem>>()
    val movieList: LiveData<List<MovieListItem>> get() = _movies

    init {
        _movies.value = mapMovieList()
    }

    fun getMovie(position: Int) = movies.getOrNull(position)

    private fun mapMovieList(): List<MovieListItem> = movies.map {
        MovieListItem(
            id = it.id,
            poster = it.posterPath,
            title = it.title,
            year = dateHandler.getYearFromMillis(dateHandler.parseFromYearMonthDay(it.releaseDate)).toString(),
            rating = it.voteAvg,
            ratingCount = it.voteCount,
            tags = movieTagBuilder(it)
        )
    }
}