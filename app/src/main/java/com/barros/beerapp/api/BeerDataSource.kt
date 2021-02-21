package com.barros.beerapp.api

import com.barros.beerapp.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BeerDataSource {

    protected suspend fun <T> getResult(call: suspend () -> T): Result<T> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = call()
            Timber.i("Network Success $response")
            Result.Success(response)
        } catch (throwable: Throwable) {
            Timber.e("Network Error $throwable")
            Result.Error("Network Error $throwable")
        }
    }
}
