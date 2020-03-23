package com.demo.popcornapp.data.usecase

import com.demo.popcornapp.data.local.SharedPreferences

class SaveItemToSuggestionListUseCase(private val getSuggestionList: GetSuggestionListUseCase, private val preferences: SharedPreferences) {

    operator fun invoke(suggestion: String) {
        val existing = getSuggestionList().toMutableList()

        if (existing.contains(suggestion)) {
            return
        }

        existing.add(0, suggestion)

        preferences.suggestions = existing
            .take(GetSuggestionListUseCase.SUGGESTION_LIST_SIZE)
            .joinToString(separator = GetSuggestionListUseCase.SUGGESTION_LIST_DIVIDER)
    }
}