package com.solodev.animeloom.di

import android.app.Application
import com.solodev.animeloom.data.manager.LocalUserManagerImpl
import com.solodev.animeloom.domain.manager.LocalUserManager
import com.solodev.animeloom.domain.repository.AnimeRepository
import com.solodev.animeloom.domain.repository.MangaRepository
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.AppEntryUseCases
import com.solodev.animeloom.domain.usecase.anime.DeleteAnime
import com.solodev.animeloom.domain.usecase.appentry.ReadAppEntry
import com.solodev.animeloom.domain.usecase.appentry.SaveAppEntry
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCategories
import com.solodev.animeloom.domain.usecase.anime.SelectAnime
import com.solodev.animeloom.domain.usecase.anime.SelectAnimeById
import com.solodev.animeloom.domain.usecase.anime.UpsertAnime
import com.solodev.animeloom.domain.usecase.manga.DeleteManga
import com.solodev.animeloom.domain.usecase.manga.GetManga
import com.solodev.animeloom.domain.usecase.manga.GetMangaId
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
        animeRepository: AnimeRepository,
    ): AnimeUseCases {
        return AnimeUseCases(
            getAnimes = GetAnime(animeRepository),
            getAnimeId = GetAnimeId(animeRepository),
            getCategories = GetCategories(animeRepository),
            upsertAnime = UpsertAnime(animeRepository),
            deleteAnime = DeleteAnime(animeRepository),
            selectAnime = SelectAnime(animeRepository),
            selectAnimeById = SelectAnimeById(animeRepository)

        )
    }

    @Provides
    @Singleton
    fun provideMangaUseCases(
        mangaRepository: MangaRepository
    ) : MangaUseCases{
        return MangaUseCases(
            getManga = GetManga(mangaRepository),
            getMangaId = GetMangaId(mangaRepository),
            upsertManga = UpsertManga(mangaRepository),
            deleteManga = DeleteManga(mangaRepository),
            selectManga = SelectManga(mangaRepository),
            selectMangaById = SelectMangaById(mangaRepository)
        )
    }
}