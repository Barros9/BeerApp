package com.barros.beerapp.api

import com.barros.beerapp.model.BeerItem
import retrofit2.Response
import timber.log.Timber

abstract class BeerDataSource {

    protected suspend fun getResult(call: suspend () -> Response<List<BeerItem>>): List<BeerItem> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Timber.i("Network Success $body")
                    return body
                }
            }
            return listOf()
        } catch (e: Exception) {
            return listOf()
        }
    }
}
