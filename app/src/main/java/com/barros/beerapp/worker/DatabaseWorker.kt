package com.barros.beerapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.barros.beerapp.database.BeerDatabase
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.util.DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {
            try {
                @Suppress("BlockingMethodInNonBlockingContext")
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    val list = Json.decodeFromString<List<BeerItem>>(jsonString)
                    BeerDatabase.getInstance(applicationContext).beerDao().insertBeers(list)
                    Result.success()
                }
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}
