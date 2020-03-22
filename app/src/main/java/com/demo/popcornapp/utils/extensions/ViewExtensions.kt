package com.demo.popcornapp.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Helper extension which hides the system soft keyboard.
 */
fun View?.hideKeyboard() {
    this?.let { view ->
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }
}