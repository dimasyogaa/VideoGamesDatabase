package com.yogadimas.videogamesdatabaseyogadimaspambudi.data.networking


import com.yogadimas.videogamesdatabaseyogadimaspambudi.BuildConfig
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.GameDetailResponse
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.GamesListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {


    @GET("games")
    suspend fun getAllGames(@Query("key") key: String = BuildConfig.KEY): GamesListResponse


    @GET("games")
    suspend fun getSearchGame(
        @Query("search") keyword: String,
        @Query("key") key: String = BuildConfig.KEY
    ): GamesListResponse

    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.KEY
    ): GameDetailResponse

    @GET("games")
    suspend fun getAllGamesPaging(
        @Query("page") page: Int,
        @Query("page_size") size: Int,
        @Query("key") key: String = BuildConfig.KEY
    ): GamesListResponse




}