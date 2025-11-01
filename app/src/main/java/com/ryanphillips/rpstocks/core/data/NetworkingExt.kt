package com.ryanphillips.rpstocks.core.data

import kotlinx.coroutines.CancellationException
import retrofit2.Response

/**
 * Handle the try/catch nature of networking calls in a single place.
 * We could extend this with more granular exception handling.
 */
suspend inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: Exception) {
        if (e is CancellationException) throw e // Rethrow Coroutine cancellations so they are handled properly by parent scopes.
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Depends on the Retrofit Response<T> class that holds the HTTP code of the response
 * which allows us to create and return our custom Result type that wraps the data
 * or DataError's that we have previously defined.
 */
suspend inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when (response.code()) {
        in 200 .. 299 -> {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(DataError.Network.PARSE_ERROR)
        }
        in 500 .. 599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}