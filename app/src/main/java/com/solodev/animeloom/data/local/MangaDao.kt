package com.solodev.animeloom.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodev.animeloom.domain.model.Attributes
import com.solodev.animeloom.domain.model.MangaData
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mangaData: MangaData)

    @Query("DELETE FROM manga_data WHERE localId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM manga_data")
    fun selectManga(): Flow<List<MangaData>>

    @Query("SELECT * FROM manga_data WHERE id=:id")
    suspend fun selectMangaById(id: String): MangaData?

    @Query("UPDATE manga_data SET attributes = :attributes WHERE id = :id")
    suspend fun updateAttributesById(id: String, attributes: Attributes)

    suspend fun upsert(mangaData: MangaData) {
        val existingManga = selectMangaById(mangaData.id)

        if (existingManga == null) {
            insert(mangaData)
        } else {
            mangaData.attributes?.let {
                updateAttributesById(mangaData.id, it)
            }
        }
    }
}
