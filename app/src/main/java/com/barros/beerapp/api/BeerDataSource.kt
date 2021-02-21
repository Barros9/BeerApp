package com.barros.beerapp.api

import com.barros.beerapp.model.Result
import retrofit2.Response
import timber.log.Timber

abstract class BeerDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Timber.i("Network Success $body")
                    return Result.Success(body)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.Error("Network call has failed for a following reason: $message")
    }
}
