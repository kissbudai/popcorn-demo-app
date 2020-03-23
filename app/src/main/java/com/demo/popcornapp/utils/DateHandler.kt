package com.demo.popcornapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Utility methods for parsing/formatting times.
 */
class DateHandler {

    private val formatter = SimpleDateFormat("yyyy-mm-dd", Locale.US)

    /**
     * Calculates the time in milliseconds from the provided date string.
     *
     * @param date String in format yyyy-mm-dd
     *
     * @return time in millis from [date] or 0 if the provided string is not in the correct format
     */
    fun parseFromYearMonthDay(date: String): Long = try {
        formatter.parse(date)?.time ?: 0
    } catch (e: ParseException) {
        0
    }

    /**
     * Determines the year from [timeInMillis].
     *
     * @param timeInMillis The time in milliseconds.
     *
     * @return The year.
     */
    fun getYearFromMillis(timeInMillis: Long): Int =
        Calendar.getInstance(Locale.getDefault()).apply { this.timeInMillis = timeInMillis }.get(Calendar.YEAR)
}