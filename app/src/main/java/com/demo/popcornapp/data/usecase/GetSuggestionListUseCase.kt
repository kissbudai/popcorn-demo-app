package com.demo.popcornapp.data.usecase

import com.demo.popcornapp.data.local.SharedPreferences

class GetSuggestionListUseCase(private val preferences: SharedPreferences) {

    operator fun invoke(): List<String> = preferences.suggestions
        .split(SUGGESTION_LIST_DIVIDER)
        .take(SUGGESTION_LIST_SIZE)
        .filter { it.isNotEmpty() }

    companion object {
        const val SUGGESTION_LIST_DIVIDER = ","
        const val SUGGESTION_LIST_SIZE = 10
    }
}