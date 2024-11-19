package com.solodev.animeloom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData

@Database(entities = [AnimeData::class, MangaData::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AnimeLoomDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun mangaDao(): MangaDao
}
