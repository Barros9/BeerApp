package com.barros.beerapp.libraries.beer.data.repository

import com.barros.beerapp.libraries.beer.domain.entity.Beer
import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.model.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun singleSourceOfTruthStrategy(
    readLocalData: suspend () -> List<Beer>,
    readRemoteData: suspend () -> List<Beer>,
    saveLocalData: suspend (List<Beer>) -> Unit,
): Flow<Result<List<Beer>>> = flow {
    val localData = getResult { readLocalData() }
    if (localData is Result.Success && localData.data.isNotEmpty()) {
        emit(localData)
    } else {
        val remoteData = getResult { readRemoteData() }
        if (remoteData is Result.Success) {
            if (remoteData.data.isNotEmpty()) {
                saveLocalData(remoteData.data)
                val localDataUpdated = getResult { readLocalData() }
                emit(localDataUpdated)
            }
        } else {
            emit(Result.Error("Error", (remoteData as Result.Error).throwable))
        }
    }
}