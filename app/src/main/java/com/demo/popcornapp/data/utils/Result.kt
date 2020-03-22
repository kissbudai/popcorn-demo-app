package com.demo.popcornapp.data.utils

/**
 * Wrapper class for data exposed from the data layer. Every use case exposes this wrapper class.
 *
 * Easy way to wrap the result of a data action with [invoke].
 */
sealed class Result<out T> {

    /**
     * Result containing the success data.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Result container for any error in the data layer.
     */
    data class Error<out T>(val throwable: Throwable) : Result<T>()

    companion object {

        suspend fun <T> invoke(f: suspend () -> T): Result<T> = try {
            Success(f())
        } catch (throwable: Throwable) {
            Error(throwable)
        }
    }
}