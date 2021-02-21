package com.barros.beerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.model.BeerItem

@Dao
interface BeerDao {

    @Query("SELECT * FROM beeritem")
    suspend fun getBeers(): List<BeerItem>

    @Query("SELECT * FROM beeritem WHERE name LIKE :search")
    suspend fun getBeersFilteredByName(search: String): List<BeerItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<BeerItem>)
}
