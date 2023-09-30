package com.barros.beerapp.libraries.beer.data.repository

import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.model.Result.Error
import com.barros.beerapp.libraries.beer.domain.model.Result.Success
import com.barros.beerapp.libraries.beer.domain.model.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> singleSourceOfTruthStrategy(
    readLocalData: suspend () -> T,
    readRemoteData: suspend () -> T,
    saveLocalData: suspend (T) -> Unit
): Flow<Result<T>> = flow {
    // Try to read local data, we are using a prepopulate database
    when (val localData = getResult { readLocalData() }) {
        is Success -> {
            // If Success, emit local data
            emit(localData)

            // Meanwhile check for new remote data
            when (val remoteData = getResult { readRemoteData() }) {
                is Success -> {
                    // If Success, rewrite local data with new ones and emit again
                    saveLocalData(remoteData.data)
                    when (val localDataUpdated = getResult { readLocalData() }) {
                        is Success -> emit(localDataUpdated)
                        is Error -> emit(Error("Error reading data", localDataUpdated.throwable))
                    }
                }
                is Error -> {
                    // If Error, do nothing, local data already emitted
                }
            }
        }
        is Error -> {
            // If Error, emit the error, it means that something went wrong reading database
            emit(Error("Error reading data", localData.throwable))
        }
    }
}
