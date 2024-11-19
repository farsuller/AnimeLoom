package com.solodev.animeloom.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.Attributes
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animeData: AnimeData)

    @Query("DELETE FROM anime_data WHERE localId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM anime_data")
    fun selectAnimes(): Flow<List<AnimeData>>

    @Query("SELECT * FROM anime_data WHERE id=:id")
    suspend fun selectAnimeById(id: String): AnimeData?

    @Query("UPDATE anime_data SET attributes = :attributes WHERE id = :id")
    suspend fun updateAttributesById(id: String, attributes: Attributes)

    suspend fun upsert(animeData: AnimeData) {
        val existingAnime = selectAnimeById(animeData.id)

        if (existingAnime == null) {
            insert(animeData)
        } else {
            animeData.attributes?.let {
                updateAttributesById(animeData.id, it)
            }
        }
    }
}
