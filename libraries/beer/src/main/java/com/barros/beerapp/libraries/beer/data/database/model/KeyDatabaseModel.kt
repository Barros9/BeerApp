package com.barros.beerapp.libraries.beer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class KeyDatabaseModel(
    @PrimaryKey
    val beerId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)