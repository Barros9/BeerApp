package com.barros.beerapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.worker.DatabaseWorker

@Database(entities = [BeerItem::class], version = 2, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        @Volatile private var instance: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): BeerDatabase {
            return Room.databaseBuilder(context, BeerDatabase::class.java, "beerapp.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<DatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}
