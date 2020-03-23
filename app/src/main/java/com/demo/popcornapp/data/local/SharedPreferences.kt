package com.demo.popcornapp.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferences private constructor(private val preferences: SharedPreferences) {

    var suggestions: String by FieldDelegate.String("suggestions", "")

    private sealed class FieldDelegate<T>(protected val key: kotlin.String, protected val default: T) :
        ReadWriteProperty<com.demo.popcornapp.data.local.SharedPreferences, T> {

        class String(key: kotlin.String, default: kotlin.String) : FieldDelegate<kotlin.String>(key, default) {

            override fun getValue(thisRef: com.demo.popcornapp.data.local.SharedPreferences, property: KProperty<*>): kotlin.String =
                thisRef.preferences.getString(key, default) ?: default

            override fun setValue(thisRef: com.demo.popcornapp.data.local.SharedPreferences, property: KProperty<*>, value: kotlin.String) {
                thisRef.preferences.edit().putString(key, value).apply()
            }
        }
    }

    companion object {
        private const val NAME = "popCornLocalStorage"

        fun create(context: Context) = SharedPreferences(context.getSharedPreferences(NAME, Context.MODE_PRIVATE))
    }
}