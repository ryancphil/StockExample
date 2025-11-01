package com.ryanphillips.rpstocks.core.domain

// MyError Interface
interface MyError

sealed interface DataError: MyError {
    enum class Network: DataError {
        NO_INTERNET,
        SERVER_ERROR,
        PARSE_ERROR,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL
    }
}

sealed interface Result<out D, out E> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: MyError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: MyError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: MyError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {}
}

typealias EmptyResult<E> = Result<Unit, E>


