package com.solodev.animeloom.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.domain.usecase.AnimeUseCases
import com.solodev.animeloom.presentation.screens.home.states.AnimeState
import com.solodev.animeloom.presentation.screens.home.states.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeAnimeViewModel @Inject constructor(
    private val animesUseCases: AnimeUseCases,
) : ViewModel() {

    private val _animeState = MutableStateFlow(AnimeState())
    val animeState: StateFlow<AnimeState> = _animeState.asStateFlow()

    private val _animeHighRateState = MutableStateFlow(AnimeState())
    val animeHighRateState: StateFlow<AnimeState> = _animeHighRateState.asStateFlow()

    private val _animeRomanceState = MutableStateFlow(AnimeState())
    val animeRomanceState: StateFlow<AnimeState> = _animeRomanceState.asStateFlow()

    private val _trendingAnimeState = MutableStateFlow(AnimeState())
    val trendingAnimeState: StateFlow<AnimeState> = _trendingAnimeState.asStateFlow()

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

    init {
        requestApis()
    }


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
            val fetchAnimes = when (selectedSeeAll) {
                "Trending Anime" -> { animesUseCases.getTrendingAnimes() }
                "Upcoming Anime" -> { animesUseCases.getAnime(status = "upcoming", limit = 15, sort = "-user_count") }
                "Highest Rated Anime" -> { animesUseCases.getAnime(limit = 15, sort = "-average_rating") }
                "Anime" -> { animesUseCases.getAnime(status = "upcoming", limit = 15, sort = "-start_date") }
                else -> return@launch
            }

            fetchAnimes
                .onStart { _animeBySeeAllState.value = _animeBySeeAllState.value.copy(isLoading = true) }
                .catch { e -> _animeBySeeAllState.value = _animeBySeeAllState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                ) }
                .collectLatest { result ->
                    val animes = result.body()?.data?.map { it.toModel() }
                    _animeBySeeAllState.value = _animeBySeeAllState.value.copy(animeDataList = animes)
                    _animeBySeeAllState.value = _animeBySeeAllState.value.copy(isLoading = false)
                }
        }
    }

    private fun getTrendingAnimes() {
        viewModelScope.launch {
            animesUseCases.getTrendingAnimes()
                .onStart {
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(errorMessage = e.message)
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val trendingAnimes = result.body()?.data?.map { it.toModel() }
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(animeDataList = trendingAnimes)
                    _trendingAnimeState.value = _trendingAnimeState.value.copy(isLoading = false)
                }
        }
    }

    private fun getUpcomingAnimes() {
        viewModelScope.launch {
            animesUseCases.getAnime(status = "upcoming", limit = 15, sort = "-user_count")
                .onStart {
                    _animeState.value = _animeState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _animeState.value = _animeState.value.copy(errorMessage = e.message)
                    _animeState.value = _animeState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val animes = result.body()?.data?.map { it.toModel() }
                    _animeState.value = _animeState.value.copy(animeDataList = animes)
                    _animeState.value = _animeState.value.copy(isLoading = false)
                }
        }
    }

    private fun getAnimes() {
        viewModelScope.launch {
            animesUseCases.getAnime(status = "upcoming", limit = 15, sort = "-start_date")
                .onStart {
                    _animeRomanceState.value = _animeRomanceState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _animeRomanceState.value = _animeRomanceState.value.copy(errorMessage = e.message)
                    _animeRomanceState.value = _animeRomanceState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val animes = result.body()?.data?.map { it.toModel() }
                    _animeRomanceState.value = _animeRomanceState.value.copy(animeDataList = animes)
                    _animeRomanceState.value = _animeRomanceState.value.copy(isLoading = false)
                }
        }
    }

    fun getAnimesByCategory(categorySelected: String?) {
        viewModelScope.launch {
            animesUseCases.getAnime(status = "current", categories = categorySelected, limit = 15, sort = "-start_date")
                .onStart {
                    _animeByCategoryState.value = _animeByCategoryState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _animeByCategoryState.value = _animeByCategoryState.value.copy(errorMessage = e.message)
                    _animeByCategoryState.value = _animeByCategoryState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val animes = result.body()?.data?.map { it.toModel() }
                    _animeByCategoryState.value = _animeByCategoryState.value.copy(animeDataList = animes)
                    _animeByCategoryState.value = _animeByCategoryState.value.copy(isLoading = false)
                }
        }
    }

    private fun getHighestRatedAnimes() {
        viewModelScope.launch {
            animesUseCases.getAnime(limit = 15, sort = "-average_rating")
                .onStart {
                    _animeHighRateState.value = _animeHighRateState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _animeHighRateState.value = _animeHighRateState.value.copy(errorMessage = e.message)
                    _animeHighRateState.value = _animeHighRateState.value.copy(isLoading = false)
                }.collectLatest { result ->
                    val animes = result.body()?.data?.map { it.toModel() }
                    _animeHighRateState.value = _animeHighRateState.value.copy(animeDataList = animes)
                    _animeHighRateState.value = _animeHighRateState.value.copy(isLoading = false)
                }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            animesUseCases.getCategories()
                .onStart {
                    _categoryState.value = _categoryState.value.copy(isLoading = true)
                }
                .catch { e ->
                    _categoryState.value = _categoryState.value.copy(errorMessage = e.message)
                    _categoryState.value = _categoryState.value.copy(isLoading = false)
                }.collectLatest { result ->

                    val categories = result.body()?.data?.map { it.toModel() }
                    _categoryState.value = _categoryState.value.copy(categories = categories)
                    _categoryState.value = _categoryState.value.copy(isLoading = false)
                }
        }
    }
}
