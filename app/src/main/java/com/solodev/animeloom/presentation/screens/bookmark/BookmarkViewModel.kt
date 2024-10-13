package com.solodev.animeloom.presentation.screens.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.domain.usecase.AnimesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val animesUseCases: AnimesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getAnimes()
    }

    private fun getAnimes() {
//        animesUseCases.selectAnimes().onEach {
//            _state.value = _state.value.copy(animes = it.asReversed())
//        }.launchIn(viewModelScope)
    }
}
