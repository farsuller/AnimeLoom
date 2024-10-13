package com.solodev.animeloom.di

import android.app.Application
import com.solodev.animeloom.data.manager.LocalUserManagerImpl
import com.solodev.animeloom.domain.manager.LocalUserManager
import com.solodev.animeloom.domain.repository.AnimeRepository
import com.solodev.animeloom.domain.usecase.AnimesUseCases
import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.appentry.AppEntryUseCases
import com.solodev.animeloom.domain.usecase.appentry.ReadAppEntry
import com.solodev.animeloom.domain.usecase.appentry.SaveAppEntry
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
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
    ): AnimesUseCases {
        return AnimesUseCases(
            getAnimes = GetAnime(animeRepository),
            getAnimeId = GetAnimeId(animeRepository),
        )
    }
}