package com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.dao.GameFavoriteDao
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities.GameFavoriteEntity

@Database(entities = [GameFavoriteEntity::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameFavoriteDao(): GameFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GameDatabase {
            if (INSTANCE == null) {
                synchronized(GameDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java, "db_game"
                    ).build()
                }
            }
            return INSTANCE as GameDatabase
        }
    }

}