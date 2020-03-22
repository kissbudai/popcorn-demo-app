package com.demo.popcornapp.utils.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Extension for making a statement as returning function.
 *
 * It is recommended to be used with `when` statements for forcing them to define each case.
 */
val <T> T.exhaustive: T get() = this

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)