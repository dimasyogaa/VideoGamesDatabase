package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository

class DetailViewModel(private val gameRepository: GameRepository) : ViewModel() {
    fun getDetailGame(id: Int) = gameRepository.getDetailGame(id).asLiveData()
}