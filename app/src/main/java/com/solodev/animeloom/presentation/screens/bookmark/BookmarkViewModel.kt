package com.solodev.animeloom.presentation.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
    private val mangaUseCases: MangaUseCases,
) : ViewModel() {

    private val _bookMarkState = MutableStateFlow(BookmarkState())
    val bookmarkState: StateFlow<BookmarkState> = _bookMarkState.asStateFlow()

    private val _isLoadingData = MutableStateFlow(false)
    val isLoadingData = _isLoadingData
        .onStart {
            requestApis()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private fun requestApis() {
        viewModelScope.launch {
            getBookmarkedAnimes()
            getBookmarkedManga()
        }
    }

    private fun getBookmarkedAnimes() {
        animesUseCases.selectAnime().onEach { anime ->
            _bookMarkState.update {
                it.copy(bookMarkAnimeList = anime.asReversed())
            }
        }.launchIn(viewModelScope)
    }

    private fun getBookmarkedManga() {
        mangaUseCases.selectManga().onEach { manga ->
            _bookMarkState.update {
                it.copy(bookMarkMangaList = manga.asReversed())
            }
        }.launchIn(viewModelScope)
    }
}
