package com.demo.popcornapp.data.usecase

import com.demo.popcornapp.data.local.SharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

class SaveItemToSuggestionListUseCaseTest {

    private val getSuggestionListUseCase: GetSuggestionListUseCase = mock()
    private val preferences: SharedPreferences = mock()

    private lateinit var sut: SaveItemToSuggestionListUseCase

    @Before
    fun setup() {
        sut = SaveItemToSuggestionListUseCase(getSuggestionListUseCase, preferences)
    }

    @Test
    fun `GIVEN empty existing suggestions WHEN a suggestion is saved THEN a one element list is written into preferences`() {
        whenever(getSuggestionListUseCase.invoke()).doReturn(emptyList())

        sut.invoke("test")

        verify(preferences, times(1)).suggestions = "test"
    }

    @Test
    fun `GIVEN existing suggestions WHEN a new suggestion is saved THEN the new suggestion is appended as a first element divided with ,s`() {
        whenever(getSuggestionListUseCase.invoke()).doReturn(listOf("b", "c", "d"))

        sut.invoke("a")

        verify(preferences, times(1)).suggestions = "a,b,c,d"
    }

    @Test
    fun `GIVEN existing suggestions WHEN a new suggestion is saved wants to be saved which already exists THEN nothing new is saved`() {
        whenever(getSuggestionListUseCase.invoke()).doReturn(listOf("a", "b", "c", "d"))

        sut.invoke("a")

        verify(preferences, times(0)).suggestions = any()
    }
}