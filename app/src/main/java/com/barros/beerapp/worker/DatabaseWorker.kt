package com.barros.beerapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.barros.beerapp.database.BeerDatabase
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Keys
import com.barros.beerapp.util.DATA_FILENAME
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okio.Okio
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
                    JsonReader.of(Okio.buffer(Okio.source(inputStream))).use { jsonReader ->
                        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                        val listType = Types.newParameterizedType(List::class.java, BeerItem::class.java)
                        val adapter: JsonAdapter<List<BeerItem>> = moshi.adapter(listType)

                        val beerList = adapter.fromJson(jsonReader)
                        BeerDatabase.getInstance(applicationContext).beerDao().insertAll(beerList!!)

                        val keysList = beerList.map {
                            Keys(
                                it.id,
                                it.id - 1,
                                it.id + 1
                            )
                        }
                        BeerDatabase.getInstance(applicationContext).keysDao().insertAll(keysList)

                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}
