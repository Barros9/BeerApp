package com.barros.beerapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "beeritem")
@Parcelize
data class BeerItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    @Json(name = "image_url")
    val imageUrl: String? = ""
) : Parcelable
