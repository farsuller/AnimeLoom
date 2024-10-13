package com.solodev.animeloom.presentation.screens.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.common.AnimeList
import com.solodev.animeloom.presentation.common.SearchBar
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animeState: AnimeState,
    bookmarkState: BookmarkState,
    navigateToSearch: () -> Unit,
    navigateToDetails: (AnimeData) -> Unit,
    onNavigate: (String) -> Unit,
    onPullRefresh: () -> Unit,
) {

    var isRefreshing by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(500)
                onPullRefresh()
                isRefreshing = false
            }
        },
    )

    LaunchedEffect(Unit) {
        onNavigate(Route.HomeRoute.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(start = 14.dp, end = 14.dp),
    ) {

        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {},
        )


        when {
            animeState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            animeState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${animeState.errorMessage}")
                }
            }

            animeState.animeData != null -> {
                Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                    AnimeList(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                        animeData = animeState.animeData,
                        onClick = {
                            navigateToDetails(it)
                        },
                    )

                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }
        }


    }
}
