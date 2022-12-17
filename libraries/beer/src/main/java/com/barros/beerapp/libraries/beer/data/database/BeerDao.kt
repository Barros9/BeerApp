package com.barros.beerapp.libraries.beer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barros.beerapp.libraries.beer.data.database.model.BeerDatabaseModel

@Dao
internal interface BeerDao {
    @Query("SELECT * FROM beers WHERE (:beerName IS NULL OR name LIKE :beerName) ORDER BY id ASC LIMIT :limitPerPage OFFSET :offset")
    suspend fun getBeers(beerName: String?, offset: Int, limitPerPage: Int): List<BeerDatabaseModel>

    @Query("SELECT * FROM beers WHERE id LIKE :beerId")
    suspend fun getBeerById(beerId: Int): BeerDatabaseModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<BeerDatabaseModel>)
}
