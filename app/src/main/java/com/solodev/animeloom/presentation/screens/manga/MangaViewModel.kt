package com.solodev.animeloom.presentation.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.MangaUseCases
import com.solodev.animeloom.presentation.screens.manga.states.MangaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
) : ViewModel() {

    private val _combinedMangaState = MutableStateFlow(MangaState())
    val combinedMangaState: StateFlow<MangaState> = _combinedMangaState.asStateFlow()

    private val _isLoadingData = MutableStateFlow(false)
    val isLoadingData = _isLoadingData
        .onStart {
            requestApis()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun requestApis() {
        viewModelScope.launch {
            getCombinedMangaStates()
        }
    }

    private suspend fun fetchManga(
        status: String? = null,
        limit: Int? = null,
        sort: String? = null,
    ): Flow<MangaState> {
        return mangaUseCases.getManga(status = status, limit = limit, sort = sort)
            .onStart {
                MangaState(isLoading = true)
            }
            .catch { e ->
                MangaState(errorMessage = e.message, isLoading = false)
            }
            .map { result ->
                val mangaList = result.body()?.data?.map { it.toModel() }
                MangaState(mangaList = mangaList)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCombinedMangaStates() {
        viewModelScope.launch {
            flowOf(
                fetchManga(status = "upcoming", limit = 15, sort = "-start_date"),
                fetchManga(status = "current", limit = 15, sort = "-start_date"),
                fetchManga(limit = 15, sort = "-average_rating"),
                fetchManga(limit = 15, sort = "-user_count"),
            )
                .flattenMerge()
                .collectLatest { state ->
                    val updatedList = _combinedMangaState.value.mangaList.orEmpty() + (state.mangaList ?: emptyList())
                    _combinedMangaState.update { it.copy(mangaList = updatedList) }
                }
        }
    }
}
