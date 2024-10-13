package com.solodev.animeloom.data.remote


import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingAnimeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface AnimeApi {
    @GET("trending/anime")
    suspend fun getTrendingAnimeList(): Response<TrendingAnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<AnimeResponse>

    @GET("episodes")
    suspend fun getEpisodes(): Response<AnimeResponse>
}