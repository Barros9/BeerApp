package com.barros.beerapp.libraries.beer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel

@Database(
    entities = [BeerDatabaseModel::class],
    version = 1,
    exportSchema = false
)

internal abstract class BeerAppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
}
