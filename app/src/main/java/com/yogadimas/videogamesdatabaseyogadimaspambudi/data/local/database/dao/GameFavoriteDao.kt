package com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities.GameFavoriteEntity

@Dao
interface GameFavoriteDao {

    @Query("SELECT * FROM fav_game")
    fun getFavUser(): LiveData<List<GameFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavUser(game: GameFavoriteEntity)

    @Delete
    suspend fun deleteFavUser(game: GameFavoriteEntity)

    @Query("SELECT * FROM fav_game WHERE id = :id")
    fun getFavoriteGameById(id: Int): LiveData<GameFavoriteEntity>
}