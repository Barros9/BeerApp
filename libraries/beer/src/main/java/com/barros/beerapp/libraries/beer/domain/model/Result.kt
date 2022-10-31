package com.barros.beerapp.libraries.beer.domain.model

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String, val throwable: Throwable) : Result<Nothing>()
}

suspend fun <T> getResult(invoke: suspend () -> T): Result<T> {
    return runCatching {
        Result.Success(invoke())
    }.getOrElse {
        Result.Error("Error", it)
    }
}
