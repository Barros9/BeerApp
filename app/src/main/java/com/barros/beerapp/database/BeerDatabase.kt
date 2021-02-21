package com.barros.beerapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Keys

@Database(entities = [BeerItem::class, Keys::class], version = 3, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun keysDao(): KeysDao

    companion object {
        @Volatile private var instance: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                BeerDatabase::class.java, "beerapp.db")
                .build()
    }
}
