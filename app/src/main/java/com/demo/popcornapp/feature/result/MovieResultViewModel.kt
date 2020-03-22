package com.demo.popcornapp.feature.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MovieResultViewModel(val searchQuery: String, private val movies: List<MovieUiModel>) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieListItem>>()
    val movieList: LiveData<List<MovieListItem>> get() = _movies

    private val releaseDateParser = SimpleDateFormat("yyyy-mm-dd", Locale.US)

    init {
        _movies.value = mapMovieList()
    }

    private fun mapMovieList(): List<MovieListItem> = movies.map {
        MovieListItem(
            id = it.id,
            poster = it.posterPath,
            title = it.title,
            year = try {
                val time = releaseDateParser.parse(it.releaseDate)?.time ?: 0
                Calendar.getInstance(Locale.getDefault()).apply { timeInMillis = time }.get(Calendar.YEAR).toString()
            } catch (e: ParseException) {
                ""
            },
            rating = it.voteAvg,
            ratingCount = it.voteCount,
            tags = "TODO"
        )
    }
}