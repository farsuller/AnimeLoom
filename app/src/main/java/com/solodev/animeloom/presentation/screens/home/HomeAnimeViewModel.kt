package com.solodev.animeloom.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import com.solodev.animeloom.presentation.screens.home.states.AnimeState
import com.solodev.animeloom.presentation.screens.home.states.CategoryState
import com.solodev.animeloom.presentation.screens.home.states.TrendingAnimeState
import com.solodev.animeloom.presentation.screens.manga.states.TrendingMangaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAnimeViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
    private val mangaUseCases: MangaUseCases
) : ViewModel() {

    private val _animeState = MutableStateFlow(AnimeState())
    val animeState: StateFlow<AnimeState> = _animeState.asStateFlow()

    private val _trendingAnimeState = MutableStateFlow(TrendingAnimeState())
    val trendingAnimeState: StateFlow<TrendingAnimeState> = _trendingAnimeState.asStateFlow()

    private val _trendingMangaState = MutableStateFlow(TrendingMangaState())
    val trendingMangaState: StateFlow<TrendingMangaState> = _trendingMangaState.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    private val _isLoadingData = MutableStateFlow(false)
    val isLoadingData = _isLoadingData
        .onStart {
            requestApis()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    init {
        requestApis()
    }


    fun requestApis() {
        viewModelScope.launch {
            _isLoadingData.value = true
            getTrendingAnimes()
            getTrendingManga()
            getAnimes()
            getCategory()
            _isLoadingData.value = false
        }
    }

    private fun getTrendingAnimes() {
        viewModelScope.launch {
            animesUseCases.getTrendingAnimes()
                .onStart {
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(errorMessage = e.message)
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val trendingAnimes = result.body()?.data?.map { it.toModel() }
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(trendingAnimeList = trendingAnimes)
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = false)
                }
        }
    }

    private fun getTrendingManga() {
        viewModelScope.launch {
            mangaUseCases.getTrendingManga()
                .onStart {
                    _trendingMangaState.value = _trendingMangaState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _trendingMangaState.value = _trendingMangaState.value.copy(errorMessage = e.message)
                    _trendingMangaState.value = _trendingMangaState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val trendingManga = result.body()?.data?.map { it.toModel() }
                    _trendingMangaState.value = _trendingMangaState.value.copy(trendingMangaList = trendingManga)
                    _trendingMangaState.value = _trendingMangaState.value.copy(isLoading = false)
                }

        }
    }

    private fun getAnimes() {
        viewModelScope.launch {
            animesUseCases.getAnime()
                .onStart {
                    _animeState.value = _animeState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _animeState.value = _animeState.value.copy(errorMessage = e.message)
                    _animeState.value = _animeState.value.copy(isLoading = false)
                }.collectLatest { result ->

                    val animes = result.body()?.data?.map { it.toModel() }
                        ?.filter { it.attributes?.popularityRank != null }
                        ?.sortedBy { it.attributes?.popularityRank }

                    _animeState.value = _animeState.value.copy(animeDataList = animes)
                    _animeState.value = _animeState.value.copy(isLoading = false)
                }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            animesUseCases.getCategories()
                .onStart {
                    _categoryState.value = _categoryState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _categoryState.value = _categoryState.value.copy(errorMessage = e.message)
                    _categoryState.value = _categoryState.value.copy(isLoading = false)
                }.collectLatest { result ->

                    val categories = result.body()?.data?.map { it.toModel() }
                    _categoryState.value = _categoryState.value.copy(categories = categories)
                    _categoryState.value = _categoryState.value.copy(isLoading = false)
                }
        }
    }


}
