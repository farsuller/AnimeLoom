package com.solodev.animeloom.data.remote


import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface AnimeApi {
    @GET("trending/anime")
    suspend fun getTrendingAnimeList(): Response<AnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<AnimeResponse>

    @GET("categories")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("manga")
    suspend fun getManga(): Response<MangaListResponse>

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Response<MangaResponse>
}