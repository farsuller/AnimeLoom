package com.solodev.animeloom.presentation.screens.manga.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.usecase.AnimesUseCases
import com.solodev.animeloom.presentation.screens.home.AnimeState
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
    private val animesUseCases: AnimesUseCases,
) : ViewModel() {

    private val _mangaState = MutableStateFlow(MangaState())
    val mangaState: StateFlow<MangaState> = _mangaState.asStateFlow()

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: MangaDetailsEvent) {
        when (event) {
            is MangaDetailsEvent.UpsertDeleteAnime -> {

            }

            is MangaDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    fun getMangaById(id : Int){
        viewModelScope.launch {
            animesUseCases.getMangaId(id = id)
                .onStart {
                    _mangaState.value = MangaState(isLoading = true)
                }
                .catch { e ->
                    _mangaState.value = MangaState(errorMessage = e.message)
                }.collectLatest { result ->

                    val detail = result.body()?.data?.toModel()
                    _mangaState.value = MangaState(mangaDataDetail = detail)
                }
        }
    }


    

    private suspend fun deleteAnime(anime: AnimeData) {
//        animesUseCases.deleteAnime(anime = anime)
//        sideEffect = "DeleteAnime"
    }

    private suspend fun upsertAnime(anime: AnimeData) {
//        animesUseCases.upsertAnime(anime = anime)
//        sideEffect = "UpsertAnime"
    }
}
