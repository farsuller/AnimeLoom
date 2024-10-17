package com.solodev.animeloom.di

import com.solodev.animeloom.data.repository.AnimeRepositoryImpl
import com.solodev.animeloom.data.repository.MangaRepositoryImpl
import com.solodev.animeloom.domain.repository.AnimeRepository
import com.solodev.animeloom.domain.repository.MangaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimeRepository(animeRepositoryImpl: AnimeRepositoryImpl): AnimeRepository

    @Binds
    @Singleton
    abstract fun bindMangaRepository(mangaRepositoryImpl: MangaRepositoryImpl): MangaRepository
}