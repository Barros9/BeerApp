package com.barros.beerapp.libraries.beer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel
import com.barros.beerapp.libraries.beer.data.database.model.KeyDatabaseModel

@Database(
    entities = [BeerDatabaseModel::class, KeyDatabaseModel::class],
    version = 1,
    exportSchema = false
)

internal abstract class BeerAppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun keyDao(): KeyDao
}
