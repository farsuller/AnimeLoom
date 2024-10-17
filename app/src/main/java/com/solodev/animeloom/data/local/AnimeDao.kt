package com.solodev.animeloom.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodev.animeloom.domain.model.AnimeData
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(animeData: AnimeData)

    @Delete
    suspend fun delete(animeData: AnimeData)

    @Query("SELECT * FROM anime_data")
    fun selectAnimes(): Flow<List<AnimeData>>

    @Query("SELECT * FROM anime_data WHERE id=:id")
    suspend fun selectAnimeById(id: String): AnimeData?
}