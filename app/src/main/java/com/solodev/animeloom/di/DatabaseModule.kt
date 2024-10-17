package com.solodev.animeloom.di

import android.content.Context
import androidx.room.Room
import com.solodev.animeloom.data.local.AnimeDao
import com.solodev.animeloom.data.local.AnimeLoomDatabase
import com.solodev.animeloom.data.local.MangaDao
import com.solodev.animeloom.data.local.Converters
import com.solodev.animeloom.utils.Constants.ANIMES_LOOM_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAnimeLoomDatabase(
        @ApplicationContext context: Context
    ): AnimeLoomDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AnimeLoomDatabase::class.java,
            name = ANIMES_LOOM_DB_NAME,
        ).addTypeConverter(Converters())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(loomDatabase: AnimeLoomDatabase): AnimeDao = loomDatabase.animeDao()

    @Provides
    @Singleton
    fun provideMangaDao(loomDatabase: AnimeLoomDatabase): MangaDao = loomDatabase.mangaDao()
}