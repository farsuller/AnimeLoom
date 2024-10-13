package com.solodev.animeloom.presentation.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.usecase.AnimesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val animesUseCases: AnimesUseCases,
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteAnime -> {

            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
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
