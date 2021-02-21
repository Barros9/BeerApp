package com.barros.beerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.model.Keys

@Dao
interface KeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<Keys>)

    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun keysBeerId(id: Int): Keys?

    @Query("DELETE FROM keys")
    suspend fun clearKeys()
}
