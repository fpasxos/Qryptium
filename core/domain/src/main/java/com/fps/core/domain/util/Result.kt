package com.fps.core.domain.util

// Generic wrapper class, which wraps a Response whatever this is(Response or Error)
// Generic argument 'D' (stands for Data) for the type of data this result should wrap
// and generic argument 'E' (stands for Error)
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.fps.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

// We want to map the result, so we give 2 arguments T & E and we map this T to the type of R
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error) // just pass the Error downwards
        is Result.Success -> Result.Success(map(data)) // here if we have a result, we map it
    }
}