package com.solodev.animeloom.presentation.screens.home.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
) : ViewModel() {

    private val _animeDetailState = MutableStateFlow(AnimeDetailState())
    val animeDetailState: StateFlow<AnimeDetailState> = _animeDetailState.asStateFlow()

    private val _castingsState = MutableStateFlow(CastingsState())
    val castingsState: StateFlow<CastingsState> = _castingsState.asStateFlow()

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: AnimeDetailsEvent) {
        when (event) {
            is AnimeDetailsEvent.UpsertDeleteAnime -> {
                viewModelScope.launch {
                    val anime = animesUseCases.selectAnimeById(event.animeData.id)
                    if (anime == null) {
                        upsertAnime(animeData = event.animeData)
                    } else {
                        deleteAnime(deleteAnimeById = event.animeData.localId)
                    }
                }
            }

            is AnimeDetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }


    fun getAnimesById(id: Int) {
        viewModelScope.launch {
            animesUseCases.getAnimeId(id = id)
                .onStart {
                    _animeDetailState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch { e ->
                    _animeDetailState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message)
                    }

                }.collectLatest { result ->
                    delay(1500)
                    val detail = result.body()?.data?.toModel()
                    _animeDetailState.update {
                        it.copy(
                            animeDataDetail = detail,
                            isLoading = false)
                    }
                }
        }
    }

    fun getCastingsById(mediaId: Int?, mediaType: String?) {
        viewModelScope.launch {
            animesUseCases.getCastingsById(
                mediaType = mediaType,
                mediaId = mediaId,
                isCharacter = true,
                language = "Japanese",
                include = "character,person",
                sort = "-featured"
            )
                .onStart {
                    _castingsState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch { e ->
                    _castingsState.update {
                        it.copy(isLoading = false, errorMessage = e.message)
                    }

                }.collectLatest { result ->
                    delay(1500)
                    val castings = result.body()?.included?.map { it.toModel() }
                    _castingsState.update {
                        it.copy(isLoading = false, castingDataList = castings)
                    }
                }
        }
    }

    private suspend fun deleteAnime(deleteAnimeById: String) {
        animesUseCases.deleteAnimeById(deleteAnimeById = deleteAnimeById)
        sideEffect = "Deleted Bookmarked"
    }

    private suspend fun upsertAnime(animeData: AnimeData) {
        animesUseCases.upsertAnime(animeData = animeData)
        sideEffect = "Bookmarked"
    }
}
