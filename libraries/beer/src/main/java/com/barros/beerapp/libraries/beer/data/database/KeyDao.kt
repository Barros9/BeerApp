package com.barros.beerapp.libraries.beer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.libraries.beer.data.database.model.KeyDatabaseModel

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<KeyDatabaseModel>)

    @Query("SELECT * FROM keys WHERE beerId = :beerId")
    suspend fun getKeyById(beerId: Int): KeyDatabaseModel?

    @Query("DELETE FROM keys")
    suspend fun clear()
}