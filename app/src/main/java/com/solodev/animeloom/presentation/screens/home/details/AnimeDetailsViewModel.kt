package com.solodev.animeloom.presentation.screens.home.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.presentation.screens.home.AnimeState
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
class AnimeDetailsViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
) : ViewModel() {

    private val _animeState = MutableStateFlow(AnimeState())
    val animeState: StateFlow<AnimeState> = _animeState.asStateFlow()

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: AnimeDetailsEvent) {
        when (event) {
            is AnimeDetailsEvent.UpsertDeleteAnime -> {
                viewModelScope.launch {
                    val anime = animesUseCases.selectAnimeById(event.animeData.id)
                    if(anime == null){
                        upsertAnime(animeData = event.animeData)
                    }
                    else{
                        deleteAnime(animeData = event.animeData)
                    }
                }
            }

            is AnimeDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }


    fun getAnimesById(id : Int){
        viewModelScope.launch {
            animesUseCases.getAnimeId(id = id)
                .onStart {
                    _animeState.value = AnimeState(isLoading = true)
                }
                .catch { e ->
                    _animeState.value = AnimeState(errorMessage = e.message)
                }.collectLatest { result ->

                    val detail = result.body()?.data?.toModel()
                    _animeState.value = AnimeState(animeDataDetail = detail)
                }
        }
    }

    private suspend fun deleteAnime(animeData: AnimeData) {
        animesUseCases.deleteAnime(animeData = animeData)
        sideEffect = "DeleteAnime"
    }

    private suspend fun upsertAnime(animeData: AnimeData) {
        animesUseCases.upsertAnime(animeData = animeData)
        sideEffect = "UpsertAnime"
    }
}
