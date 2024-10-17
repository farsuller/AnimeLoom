package com.solodev.animeloom.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodev.animeloom.domain.model.MangaData
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(mangaData: MangaData)

    @Delete
    suspend fun delete(mangaData: MangaData)


    @Query("SELECT * FROM manga_data")
    fun selectManga(): Flow<List<MangaData>>

    @Query("SELECT * FROM manga_data WHERE id=:id")
    suspend fun selectMangaById(id: String): MangaData?
}