package com.yogadimas.videogamesdatabaseyogadimaspambudi.di

import android.content.Context
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.room.GameDatabase
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.networking.ApiConfig
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository

object Injection {

    fun provideRepository(context: Context): GameRepository {
        val apiService = ApiConfig.getApiService()
        val database = GameDatabase.getInstance(context)
        val dao = database.gameFavoriteDao()
        return GameRepository.getInstance(
            apiService,
            dao
        )
    }

}