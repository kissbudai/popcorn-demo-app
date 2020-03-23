package com.demo.popcornapp.feature.home

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.popcornapp.R
import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.usecase.GetMoviesForQueryUseCase
import com.demo.popcornapp.data.usecase.GetSuggestionListUseCase
import com.demo.popcornapp.data.usecase.SaveItemToSuggestionListUseCase
import com.demo.popcornapp.data.utils.Result
import com.demo.popcornapp.utils.Event
import com.demo.popcornapp.utils.ScreenState
import com.demo.popcornapp.utils.extensions.exhaustive
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class HomeViewModel(
    private val getMoviesForQuery: GetMoviesForQueryUseCase,
    private val getSuggestionList: GetSuggestionListUseCase,
    private val saveItemToSuggestionList: SaveItemToSuggestionListUseCase
) : ViewModel() {

    private val state = MutableLiveData<ScreenState>()
    val isLoading: LiveData<Boolean> = Transformations.map(state) { it == ScreenState.LOADING }

    val searchQuery = MutableLiveData<String>()

    private val _event = MutableLiveData<Event<VMEvent>>()
    val event: LiveData<Event<VMEvent>> get() = _event

    private val _suggestions = MutableLiveData<List<String>>()
    val suggestions: LiveData<List<String>> get() = _suggestions

    init {
        _suggestions.value = getSuggestionList()
    }

    /**
     * Performs the search action with value from [searchQuery] and signals back with events through [event].
     */
    fun performSearch() {

        val query = searchQuery.value

        if (query == null || query.length < 2) {
            _event.value = Event(VMEvent.ShowSearchQueryRule)
            return
        }

        state.value = ScreenState.LOADING

        viewModelScope.launch {
            when (val result = getMoviesForQuery(query)) {
                is Result.Success -> {
                    state.value = ScreenState.NORMAL

                    if (result.data.isEmpty()) {
                        _event.value = Event(VMEvent.SearchFailed(R.string.could_not_find_results))
                    } else {
                        _event.value = Event(VMEvent.SearchSucceeded(result.data))
                        saveItemToSuggestionList(query)
                        _suggestions.value = getSuggestionList()
                    }
                }
                is Result.Error -> {
                    state.value = ScreenState.ERROR

                    val errorMessageRes = when (result.throwable) {
                        is UnknownHostException -> R.string.no_connection
                        else -> R.string.something_went_wrong
                    }
                    _event.value = Event(VMEvent.SearchFailed(errorMessageRes))
                }
            }.exhaustive
        }
    }

    sealed class VMEvent {
        object ShowSearchQueryRule : VMEvent()

        data class SearchSucceeded(val movies: List<Movie>) : VMEvent()
        data class SearchFailed(@StringRes val reasonTextResId: Int) : VMEvent()
    }
}