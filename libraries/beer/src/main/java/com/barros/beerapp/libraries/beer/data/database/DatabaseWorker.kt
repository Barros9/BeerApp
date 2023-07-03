package com.barros.beerapp.libraries.beer.data.database

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@HiltWorker
internal class DatabaseWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val beerDao: Lazy<BeerDao>
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val prePopulateDatabaseFile = "pre-populate-database.json"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        runCatching {
            context.assets.open(prePopulateDatabaseFile).use { inputStream ->
                beerDao.get().insertBeers(Json.decodeFromStream(inputStream))
            }
            Result.success()
        }.getOrElse {
            Result.failure()
        }
    }
}
