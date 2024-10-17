package com.solodev.animeloom.presentation.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimesUseCases
import com.solodev.animeloom.presentation.screens.home.AnimeState
import com.solodev.animeloom.presentation.screens.home.CategoryState
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
class MangaViewModel @Inject constructor(
    private val animesUseCases: AnimesUseCases,
) : ViewModel() {

    private val _mangaState = MutableStateFlow(MangaState())
    val mangaState: StateFlow<MangaState> = _mangaState.asStateFlow()

    init {
        getManga()
    }


    private fun getManga(){
        viewModelScope.launch {
            animesUseCases.getManga()
                .onStart {
                    _mangaState.value = MangaState(isLoading = true)
                }
                .catch { e ->
                    _mangaState.value = MangaState(errorMessage = e.message)
                }.collectLatest { result ->

                    val manga = result.body()?.data?.map { it.toModel() }
                    _mangaState.value = MangaState(manga = manga)
                }
        }
    }


}
