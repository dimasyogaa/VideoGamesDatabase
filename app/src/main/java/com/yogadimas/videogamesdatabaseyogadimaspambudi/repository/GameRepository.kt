package com.yogadimas.videogamesdatabaseyogadimaspambudi.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.GameDetailResponse
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.GamesListResponse
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.ResultsItem
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.networking.ApiService
import com.yogadimas.videogamesdatabaseyogadimaspambudi.paging.GamePagingSource
import com.yogadimas.videogamesdatabaseyogadimaspambudi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class GameRepository private constructor(
    private val apiService: ApiService,
    // private val userDao: UserDao,
) {



    fun getAllGames(): Flow<Resource<GamesListResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAllGames()
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getSearchGame(keyword: String): Flow<Resource<GamesListResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getSearchGame(keyword = keyword)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailGame(id: Int): Flow<Resource<GameDetailResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getDetailGame(id = id)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllGamesPaging(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                GamePagingSource(apiService)
            }
        ).flow
    }

    companion object {

        @Volatile
        private var instance: GameRepository? = null
        fun getInstance(
            apiService: ApiService,
            // newsDao: UserDao,
        ): GameRepository =
            instance ?: synchronized(this) {
                instance ?: GameRepository(
                    apiService,
                    // newsDao
                )
            }.also { instance = it }
    }
}