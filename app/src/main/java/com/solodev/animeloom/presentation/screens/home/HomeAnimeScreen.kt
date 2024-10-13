package com.solodev.animeloom.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.solodev.animeloom.presentation.common.AnimeCard
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkState
import com.solodev.animeloom.presentation.screens.home.components.DefaultChipButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalSharedTransitionApi::class,
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SharedTransitionScope.HomeAnimesScreen(
    animeState: AnimeState,
    bookmarkState: BookmarkState,
    onAnimeClick: (String?, String?) -> Unit,
    onNavigate: (String) -> Unit,
    onPullRefresh: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
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

    val density = LocalDensity.current

    var headerSize by remember { mutableStateOf(IntSize(0, 0)) }
    val headerHeight by remember(headerSize) { mutableStateOf(with(density) { headerSize.height.toDp() }) }

    var tabsSize by remember { mutableStateOf(IntSize(0, 0)) }
    val tabsHeight by remember(tabsSize) { mutableStateOf(with(density) { tabsSize.height.toDp() }) }

    val headerOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember(headerSize) {
        object : NestedScrollConnection {
            val headerHeightPx = headerSize.height.toFloat()

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = headerOffsetHeightPx.floatValue + delta
                headerOffsetHeightPx.floatValue = newOffset.coerceIn(-headerHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(Unit) {
        onNavigate(Route.HomeRoute.route)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .zIndex(1f)
                    .offset { IntOffset(x = 0, y = headerOffsetHeightPx.floatValue.roundToInt()) }
                    .background(MaterialTheme.colorScheme.surface),
            ) {

                PosterHeader(
                    modifier = Modifier.onSizeChanged { headerSize = it },
                    animePosterHeader = animeState.animeData?.firstOrNull()?.attributes?.posterImage?.large
                )

                AnimeCategoryChips(
                    modifier = Modifier.onSizeChanged { tabsSize = it },
                    animeState = animeState
                )
            }

            when {
                animeState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                animeState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${animeState.errorMessage}")
                    }
                }

                animeState.animeData != null -> {
                    LazyColumn(
                        contentPadding = PaddingValues(top = headerHeight + tabsHeight),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(animeState.animeData) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = {
                                    onAnimeClick(
                                        anime.attributes.posterImage?.original ?: "",
                                        anime.id
                                    )
                                },
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PosterHeader(
    modifier: Modifier,
    animePosterHeader: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = animePosterHeader,
            contentDescription = animePosterHeader,
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun AnimeCategoryChips(
    modifier: Modifier,
    animeState: AnimeState
) {
    LazyRow(modifier = modifier) {
        items(5) { data ->
            DefaultChipButton(text = "Funny")
            DefaultChipButton(text = "Horror")
            DefaultChipButton(text = "Fantasy")
            DefaultChipButton(text = "Isekai")
            DefaultChipButton(text = "Comedy")
        }


    }
}