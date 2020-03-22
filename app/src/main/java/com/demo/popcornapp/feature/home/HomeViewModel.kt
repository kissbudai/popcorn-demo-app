package com.demo.popcornapp.feature.home

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.popcornapp.R
import com.demo.popcornapp.data.usecase.GetMoviesForQueryUseCase
import com.demo.popcornapp.data.model.Movie
import com.demo.popcornapp.data.utils.Result
import com.demo.popcornapp.utils.Event
import com.demo.popcornapp.utils.ScreenState
import com.demo.popcornapp.utils.extensions.exhaustive
import kotlinx.coroutines.launch

class HomeViewModel(private val getMoviesForQuery: GetMoviesForQueryUseCase) : ViewModel() {

    private val state = MutableLiveData<ScreenState>()
    val isLoading: LiveData<Boolean> = Transformations.map(state) { it == ScreenState.LOADING }

    val searchQuery = MutableLiveData<String>()

    private val _event = MutableLiveData<Event<VMEvent>>()
    val event: LiveData<Event<VMEvent>> get() = _event

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
                    _event.value = Event(VMEvent.SearchSucceeded(result.data))
                }
                is Result.Error -> {
                    state.value = ScreenState.ERROR
                    // TODO: map throwable to error.
                    _event.value = Event(VMEvent.SearchFailed(R.string.something_went_wrong))
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