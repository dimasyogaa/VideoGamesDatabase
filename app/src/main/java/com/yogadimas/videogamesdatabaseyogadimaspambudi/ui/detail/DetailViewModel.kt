package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository

class DetailViewModel(private val gameRepository: GameRepository) : ViewModel() {

    fun getAllGames() = gameRepository.getAllGames().asLiveData()


    fun getAllGamesPaging() =
        gameRepository.getAllGamesPaging().asLiveData().cachedIn(viewModelScope)

    fun getSearchGame(keyword: String) = gameRepository.getSearchGame(keyword).asLiveData()
}