package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.favorite

import androidx.lifecycle.ViewModel
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository

class FavoriteViewModel(private val gameRepository: GameRepository) : ViewModel() {
    fun getFavoriteGame() = gameRepository.getFavoriteGame()

}