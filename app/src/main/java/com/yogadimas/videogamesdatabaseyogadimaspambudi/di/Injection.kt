package com.yogadimas.videogamesdatabaseyogadimaspambudi.di

import android.content.Context
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.networking.ApiConfig
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository

object Injection {

    fun provideRepository(context: Context): GameRepository {
        val apiService = ApiConfig.getApiService()
        // val database = UserDatabase.getInstance(context)
        // val dao = database.userDao()
        return GameRepository.getInstance(
            apiService,
            // dao
        )
    }

}