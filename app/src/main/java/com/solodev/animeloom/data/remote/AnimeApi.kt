package com.solodev.animeloom.data.remote


import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AnimeApi {
    @GET("trending/anime")
    suspend fun getTrendingAnimeList(): Response<AnimeListResponse>

    @GET("anime")
    suspend fun getAnimeList(
        @Query("filter[status]") status: String? = null,
        @Query("filter[categories]") categories: String? = null,
        @Query("page[limit]") limit: Int,
        @Query("sort") sort: String
    ): Response<AnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<AnimeResponse>

    @GET("categories")
    suspend fun getCategories(
        @Query("page[limit]") limit: Int,
        @Query("sort") sort: String
    ): Response<CategoriesResponse>

    @GET("trending/manga")
    suspend fun getTrendingMangaList(): Response<TrendingMangaListResponse>

    @GET("manga")
    suspend fun getMangaList(): Response<MangaListResponse>

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Response<MangaResponse>
}