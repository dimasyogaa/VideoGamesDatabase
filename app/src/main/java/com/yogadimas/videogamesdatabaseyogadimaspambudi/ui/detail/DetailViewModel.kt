package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.local.database.entities.GameFavoriteEntity
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val gameRepository: GameRepository) : ViewModel() {
    fun getDetailGame(id: Int) = gameRepository.getDetailGame(id).asLiveData()

    fun getFavoriteGameById(id: Int) = gameRepository.getFavoriteGameById(id)

    fun insertFavoriteGame(game: GameFavoriteEntity) = viewModelScope.launch {
        gameRepository.insertFavoriteGame(game)
    }

    fun deleteFavoriteGame(game: GameFavoriteEntity) = viewModelScope.launch {
        gameRepository.deleteFavoriteGame(game)
    }

}