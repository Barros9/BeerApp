package com.barros.beerapp.libraries.beer.data.repository

import com.barros.beerapp.libraries.beer.domain.model.Result
import com.barros.beerapp.libraries.beer.domain.model.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> singleSourceOfTruthStrategy(
    readLocalData: suspend () -> T,
    readRemoteData: suspend () -> T,
    saveLocalData: suspend (T) -> Unit
): Flow<Result<T>> = flow {
    // TODO update with local data, c'è un problema con dati duplicati
    val remoteData = getResult { readRemoteData() }
    emit(remoteData)

//    when (val localData = getResult { readLocalData() }) {
//        is Result.Error -> emit(localData)
//        is Result.Success -> {
//            emit(localData)
//            when (val remoteData = getResult { readRemoteData() }) {
//                is Result.Success -> {
//                    saveLocalData(remoteData.data)
//                    val localDataUpdated = getResult { readLocalData() }
//                    emit(localDataUpdated)
//                }
//                is Result.Error -> emit(Result.Error("Error", remoteData.throwable))
//            }
//        }
//    }
}
