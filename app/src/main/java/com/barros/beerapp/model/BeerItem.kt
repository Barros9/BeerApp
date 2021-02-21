package com.barros.beerapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "beeritem")
@Parcelize
data class BeerItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String? = ""
) : Parcelable
