package com.solodev.animeloom.presentation.screens.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()
    data object SearchAnimes : SearchEvent()
}
