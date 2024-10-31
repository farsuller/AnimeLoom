package com.solodev.animeloom.presentation.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
) : ViewModel() {

    private val _mangaState = MutableStateFlow(MangaState())
    val mangaState: StateFlow<MangaState> = _mangaState.asStateFlow()

    private val _trendingMangaState = MutableStateFlow(TrendingMangaState())
    val trendingMangaState: StateFlow<TrendingMangaState> = _trendingMangaState.asStateFlow()

    init {
        getManga()
        getTrendingManga()
    }

    private fun getTrendingManga(){
        viewModelScope.launch {
            mangaUseCases.getTrendingManga()
                .onStart {
                    _trendingMangaState.value = TrendingMangaState(isLoading = true)
                }
                .catch { e ->
                    _trendingMangaState.value = TrendingMangaState(errorMessage = e.message)
                }.collectLatest { result ->
                    val trendingManga = result.body()?.data?.map { it.toModel() }
                    _trendingMangaState.value = TrendingMangaState(trendingMangaList = trendingManga)
                }

        }
    }


    private fun getManga() {
        viewModelScope.launch {
            mangaUseCases.getManga()
                .onStart {
                    _mangaState.value = MangaState(isLoading = true)
                }
                .catch { e ->
                    _mangaState.value = MangaState(errorMessage = e.message)
                }.collectLatest { result ->
                    val manga = result.body()?.data?.map { it.toModel() }
                    _mangaState.value = MangaState(manga = manga)
                }
        }
    }


}
