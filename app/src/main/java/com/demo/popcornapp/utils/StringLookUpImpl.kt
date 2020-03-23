package com.demo.popcornapp.utils

import android.content.Context

class StringLookUpImpl(private val context: Context) : StringLookup {

    override fun getString(stringRes: Int): String = context.getString(stringRes)
}