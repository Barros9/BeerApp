package com.barros.beerapp.libraries.beer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "beers")
@Serializable
internal data class BeerDatabaseModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String? = ""
)
