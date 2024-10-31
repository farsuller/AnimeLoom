package com.solodev.animeloom.presentation.screens.manga.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.domain.usecase.MangaUseCases
import com.solodev.animeloom.presentation.screens.home.details.AnimeDetailsEvent
import com.solodev.animeloom.presentation.screens.manga.MangaDetailState
import com.solodev.animeloom.presentation.screens.manga.MangaState
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
class MangaDetailsViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
) : ViewModel() {

    private val _mangaDetailState = MutableStateFlow(MangaDetailState())
    val mangaDetailState: StateFlow<MangaDetailState> = _mangaDetailState.asStateFlow()

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: MangaDetailsEvent) {
        when (event) {
            is MangaDetailsEvent.UpsertDeleteManga -> {
                viewModelScope.launch {
                    val anime = mangaUseCases.selectMangaById(event.mangaData.id)
                    if(anime == null){
                        upsertManga(manga = event.mangaData)
                    }
                    else{
                        deleteManga(manga = event.mangaData)
                    }
                }
            }

            is MangaDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    fun getMangaById(id : Int){
        viewModelScope.launch {
            mangaUseCases.getMangaId(id = id)
                .onStart {
                    _mangaDetailState.value = MangaDetailState(isLoading = true)
                }
                .catch { e ->
                    _mangaDetailState.value = MangaDetailState(errorMessage = e.message)
                }.collectLatest { result ->

                    val detail = result.body()?.data?.toModel()
                    _mangaDetailState.value = MangaDetailState(mangaDataDetail = detail)
                }
        }
    }


    

    private suspend fun deleteManga(manga: MangaData) {
        mangaUseCases.deleteManga(mangaData = manga)
        sideEffect = "DeleteManga"
    }

    private suspend fun upsertManga(manga: MangaData) {
        mangaUseCases.upsertManga(mangaData = manga)
        sideEffect = "UpsertManga"
    }
}
