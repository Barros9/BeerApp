package com.barros.beerapp.libraries.beer.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel

@Dao
internal interface BeerDao {

    @Query("SELECT * FROM beers WHERE (:beerName IS NULL OR name LIKE :beerName)")
    fun getBeers(beerName: String?): PagingSource<Int, BeerDatabaseModel>

    @Query("SELECT * FROM beers WHERE id LIKE :beerId")
    suspend fun getBeerById(beerId: Int): BeerDatabaseModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<BeerDatabaseModel>)

    @Query("DELETE FROM beers")
    suspend fun clear()
}
