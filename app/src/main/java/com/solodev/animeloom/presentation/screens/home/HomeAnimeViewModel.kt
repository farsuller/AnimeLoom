package com.solodev.animeloom.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.presentation.screens.home.states.AnimeState
import com.solodev.animeloom.presentation.screens.home.states.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeAnimeViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
) : ViewModel() {

    private val _animeNewReleaseState = MutableStateFlow(AnimeState())
    val animeNewReleaseState: StateFlow<AnimeState> = _animeNewReleaseState.asStateFlow()

    private val _animeHighRateState = MutableStateFlow(AnimeState())
    val animeHighRateState: StateFlow<AnimeState> = _animeHighRateState.asStateFlow()

    private val _animeUpcomingState = MutableStateFlow(AnimeState())
    val animeUpcomingState: StateFlow<AnimeState> = _animeUpcomingState.asStateFlow()

    private val _popularAnimeState = MutableStateFlow(AnimeState())
    val popularAnimeState: StateFlow<AnimeState> = _popularAnimeState.asStateFlow()

    private val _animeBySeeAllState = MutableStateFlow(AnimeState())
    val animeBySeeAllState: StateFlow<AnimeState> = _animeBySeeAllState.asStateFlow()

    private val _animeByCategoryState = MutableStateFlow(AnimeState())
    val animeByCategoryState: StateFlow<AnimeState> = _animeByCategoryState.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    private val _isLoadingData = MutableStateFlow(false)
    val isLoadingData = _isLoadingData
        .onStart {
            requestApis()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun requestApis() {
        viewModelScope.launch {
            _isLoadingData.value = true
            getTrendingAnimes()
            getUpcomingAnimes()
            getHighestRatedAnimes()
            getAnimes()
            getCategory()
            _isLoadingData.value = false
        }
    }

    fun getAnimeBySeeAll(selectedSeeAll: String) {
        viewModelScope.launch {
            when (selectedSeeAll) {
                "Popular Anime" -> {
                    getSeeAllAnimes(fetchAnimeData = {
                        animesUseCases.getAnime(
                            limit = 20,
                            sort = "-user_count",
                        )
                    })
                }

                "Upcoming Anime" -> {
                    getSeeAllAnimes(fetchAnimeData = {
                        animesUseCases.getAnime(
                            status = "upcoming",
                            limit = 20,
                            sort = "-user_count",
                        )
                    })
                }

                "Highest Rated Anime" -> {
                    getSeeAllAnimes(fetchAnimeData = {
                        animesUseCases.getAnime(limit = 20, sort = "-average_rating")
                    })
                }

                "Newly Released Anime" -> {
                    getSeeAllAnimes(fetchAnimeData = {
                        animesUseCases.getAnime(
                            status = "current",
                            categories = "adventure",
                            limit = 20,
                            sort = "-start_date",
                        )
                    })
                }

                else -> return@launch
            }
        }
    }

    private fun fetchAndSetAnimeState(
        state: MutableStateFlow<AnimeState>,
        fetchAnimeData: suspend () -> Flow<Response<AnimeListResponse>>,
    ) {
        viewModelScope.launch {
            fetchAnimeData()
                .onStart {
                    state.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch { e ->
                    state.update {
                        it.copy(
                            errorMessage = e.message,
                            isLoading = false,
                        )
                    }
                }
                .collectLatest { result ->
                    val animeList = result.body()?.data?.map { it.toModel() }

                    state.update {
                        it.copy(
                            animeDataList = animeList,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    private fun getSeeAllAnimes(fetchAnimeData: suspend () -> Flow<Response<AnimeListResponse>>) {
        fetchAndSetAnimeState(
            state = _animeBySeeAllState,
            fetchAnimeData = { fetchAnimeData() },
        )
    }

    private fun getTrendingAnimes() {
        fetchAndSetAnimeState(
            state = _popularAnimeState,
            fetchAnimeData = {
                animesUseCases.getAnime(
                    limit = 20,
                    sort = "-user_count",
                )
            },
        )
    }

    private fun getUpcomingAnimes() {
        fetchAndSetAnimeState(
            state = _animeUpcomingState,
            fetchAnimeData = {
                animesUseCases.getAnime(
                    status = "upcoming",
                    limit = 20,
                    sort = "-user_count",
                )
            },
        )
    }

    private fun getAnimes() {
        fetchAndSetAnimeState(
            state = _animeNewReleaseState,
            fetchAnimeData = {
                animesUseCases.getAnime(
                    status = "current",
                    categories = "adventure",
                    limit = 20,
                    sort = "-start_date",
                )
            },
        )
    }

    private fun getHighestRatedAnimes() {
        fetchAndSetAnimeState(
            state = _animeHighRateState,
            fetchAnimeData = {
                animesUseCases.getAnime(
                    limit = 20,
                    sort = "-average_rating",
                )
            },
        )
    }

    fun getAnimesByCategory(categorySelected: String?) {
        fetchAndSetAnimeState(
            state = _animeByCategoryState,
            fetchAnimeData = {
                animesUseCases.getAnime(
                    status = "current",
                    categories = categorySelected,
                    limit = 15,
                    sort = "-start_date",
                )
            },
        )
    }

    private fun getCategory() {
        viewModelScope.launch {
            animesUseCases.getCategories(limit = 40, sort = "-total_media_count")
                .onStart {
                    _categoryState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch { e ->
                    _categoryState.update {
                        it.copy(
                            errorMessage = e.message,
                            isLoading = false,
                        )
                    }
                }.collectLatest { result ->
                    val categories = result.body()?.data?.map { it.toModel() }
                    _categoryState.update {
                        it.copy(
                            categories = categories,
                            isLoading = false,
                        )
                    }
                }
        }
    }
}
