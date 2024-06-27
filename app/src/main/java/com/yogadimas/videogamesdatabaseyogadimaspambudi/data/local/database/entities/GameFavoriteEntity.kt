package com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "fav_game")
data class GameFavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "released")
    val released: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Double? = null,

    @ColumnInfo(name = "description_raw")
    val descriptionRaw: String? = null,

    ) : Parcelable




