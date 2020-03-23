package com.demo.popcornapp.utils

import androidx.annotation.StringRes

interface StringLookup {

    fun getString(@StringRes stringRes: Int): String
}