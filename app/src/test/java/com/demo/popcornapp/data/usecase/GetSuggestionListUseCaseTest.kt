package com.demo.popcornapp.data.usecase

import com.demo.popcornapp.data.local.SharedPreferences
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSuggestionListUseCaseTest {

    private val preferences: SharedPreferences = mock()

    private lateinit var sut: GetSuggestionListUseCase

    @Before
    fun setup() {
        sut = GetSuggestionListUseCase(preferences)
    }

    @Test
    fun `GIVEN empty suggestion WHEN the list is requested THEN empty list is returned`() {
        whenever(preferences.suggestions).doReturn("")

        Assert.assertTrue(sut.invoke().isEmpty())
    }

    @Test
    fun `GIVEN existing suggestions WHEN the list is requested THEN elements are returned`() {
        whenever(preferences.suggestions).doReturn("a,b,c")

        Assert.assertEquals(listOf("a", "b", "c"), sut.invoke())
    }

    @Test
    fun `GIVEN a lot of suggestion WHEN the list is requested THEN only the latest 10 elements are returned`() {
        whenever(preferences.suggestions).doReturn("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,r,s")

        Assert.assertEquals(listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"), sut.invoke())
    }
}