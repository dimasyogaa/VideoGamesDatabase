package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yogadimas.videogamesdatabaseyogadimaspambudi.di.Injection
import com.yogadimas.videogamesdatabaseyogadimaspambudi.repository.GameRepository
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.detail.DetailViewModel
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.favorite.FavoriteViewModel
import com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val gameRepository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(gameRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(gameRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(gameRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            context: Context,
        ): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}