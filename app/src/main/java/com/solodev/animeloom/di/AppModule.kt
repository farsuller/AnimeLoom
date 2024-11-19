package com.solodev.animeloom.di

import android.app.Application
import com.solodev.animeloom.data.manager.LocalUserManagerImpl
import com.solodev.animeloom.domain.manager.LocalUserManager
import com.solodev.animeloom.domain.repository.AnimeRepository
import com.solodev.animeloom.domain.repository.MangaRepository
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.AppEntryUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import com.solodev.animeloom.domain.usecase.anime.DeleteAnimeById
import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCastingsById
import com.solodev.animeloom.domain.usecase.anime.GetCategories
import com.solodev.animeloom.domain.usecase.anime.SelectAnime
import com.solodev.animeloom.domain.usecase.anime.SelectAnimeById
import com.solodev.animeloom.domain.usecase.anime.UpsertAnime
import com.solodev.animeloom.domain.usecase.appentry.ReadAppEntry
import com.solodev.animeloom.domain.usecase.appentry.SaveAppEntry
import com.solodev.animeloom.domain.usecase.manga.DeleteMangaById
import com.solodev.animeloom.domain.usecase.manga.GetManga
import com.solodev.animeloom.domain.usecase.manga.GetMangaId
import com.solodev.animeloom.domain.usecase.manga.GetTrendingManga
import com.solodev.animeloom.domain.usecase.manga.SelectManga
import com.solodev.animeloom.domain.usecase.manga.SelectMangaById
import com.solodev.animeloom.domain.usecase.manga.UpsertManga
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager,
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
    )

    @Provides
    @Singleton
    fun provideAnimesUseCases(
        repository: AnimeRepository,
    ): AnimeUseCases {
        return AnimeUseCases(
            getAnime = GetAnime(repository),
            getAnimeId = GetAnimeId(repository),
            getCastingsById = GetCastingsById(repository),
            getCategories = GetCategories(repository),
            upsertAnime = UpsertAnime(repository),
            deleteAnimeById = DeleteAnimeById(repository),
            selectAnime = SelectAnime(repository),
            selectAnimeById = SelectAnimeById(repository),
        )
    }

    @Provides
    @Singleton
    fun provideMangaUseCases(
        repository: MangaRepository,
    ): MangaUseCases {
        return MangaUseCases(
            getTrendingManga = GetTrendingManga(repository),
            getManga = GetManga(repository),
            getMangaId = GetMangaId(repository),
            upsertManga = UpsertManga(repository),
            deleteMangaById = DeleteMangaById(repository),
            selectManga = SelectManga(repository),
            selectMangaById = SelectMangaById(repository),
        )
    }
}
