package com.solodev.animeloom.presentation.screens.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
    private val mangaUseCases: MangaUseCases
) : ViewModel() {

    private val _bookMarkState = mutableStateOf(BookmarkState(bookMarkAnimeList = null, bookMarkMangaList = null))
    val bookmarkState: State<BookmarkState> = _bookMarkState

    init {
        getBookmarkedAnimes()
        getBookmarkedManga()
    }

    private fun getBookmarkedAnimes() {
        animesUseCases.selectAnime().onEach {
            _bookMarkState.value = _bookMarkState.value.copy(bookMarkAnimeList = it.asReversed())
        }.launchIn(viewModelScope)
    }

    private fun getBookmarkedManga() {
        mangaUseCases.selectManga().onEach {
            _bookMarkState.value = _bookMarkState.value.copy(bookMarkMangaList = it.asReversed())
        }.launchIn(viewModelScope)
    }
}
