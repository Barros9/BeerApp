package com.barros.beerapp.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.model.BeerItem

@Dao
interface BeerDao {

    @Query("SELECT * FROM beeritem")
    fun getBeers(): PagingSource<Int, BeerItem>

    @Query("SELECT * FROM beeritem WHERE name LIKE :search")
    fun getBeersFilteredByName(search: String): PagingSource<Int, BeerItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beers: List<BeerItem>)

    @Query("DELETE FROM beeritem")
    suspend fun clearRepos()
}
