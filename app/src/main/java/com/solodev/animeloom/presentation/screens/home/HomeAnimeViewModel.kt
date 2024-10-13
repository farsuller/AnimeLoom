package com.solodev.animeloom.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimesUseCases
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
class HomeAnimeViewModel @Inject constructor(
    private val animesUseCases: AnimesUseCases,
) : ViewModel() {

    private val _animeState = MutableStateFlow(AnimeState())
    val animeState: StateFlow<AnimeState> = _animeState.asStateFlow()


    init {
        getAnimes()
    }


    fun getAnimes(){
        viewModelScope.launch {
            animesUseCases.getAnimes()
                .onStart {
                    _animeState.value = AnimeState(isLoading = true)
                }
                .catch { e ->
                    _animeState.value = AnimeState(errorMessage = e.message)
                }.collectLatest { result ->

                    _animeState.value = AnimeState(animeData = result.body()?.data)
                }
        }
    }
}
