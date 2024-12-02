package com.solodev.animeloom.presentation.screens.home.details

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.components.DetailHeaderBar
import com.solodev.animeloom.presentation.components.ShimmerEffectCastings
import com.solodev.animeloom.presentation.components.ShimmerEffectDetailColumn
import com.solodev.animeloom.presentation.screens.home.components.CharactersItem
import com.solodev.animeloom.utils.clickableWithoutRipple

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SharedTransitionScope.AnimeDetailsScreen(
    id: String,
    localId: String = "",
    isFromBookmark: Boolean = false,
    coverImage: String,
    navigateUp: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: AnimeDetailsViewModel = hiltViewModel()
    val animeState by viewModel.animeDetailState.collectAsStateWithLifecycle()
    val castingsState by viewModel.castingsState.collectAsStateWithLifecycle()

    val animeData = animeState.animeDataDetail ?: AnimeData()
    val castingData =
        castingsState.castingDataList?.filter { it.type == "characters" } ?: emptyList()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    LaunchedEffect(true) {
        viewModel.getAnimesById(id.toInt())
        viewModel.getCastingsById(mediaId = id.toInt(), mediaType = "Anime")
    }
    if (viewModel.sideEffect != null) {
        Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
        viewModel.onEvent(AnimeDetailsEvent.RemoveSideEffect)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        NavigableListDetailPaneScaffold(
            modifier = Modifier,
            navigator = navigator,
            listPane = {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = innerPadding.calculateBottomPadding() + 10.dp),
                ) {
                    item {
                        AsyncImage(
                            model = coverImage,
                            contentDescription = null,
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = if (isFromBookmark) localId else id),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 500)
                                    },
                                )
                                .fillMaxWidth()
                                .height(400.dp)
                                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    item {
                        when {
                            animeState.isLoading -> ShimmerEffectDetailColumn()

                            animeState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            animeState.animeDataDetail != null -> {
                                val animeDataAttributes = animeData.attributes
                                val title = animeDataAttributes?.titles?.en
                                    ?: animeDataAttributes?.canonicalTitle
                                val details = animeDataAttributes?.synopsis
                                    ?: animeDataAttributes?.description

                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Spacer(modifier = Modifier.height(10.dp))

                                    DetailHeaderBar(
                                        navigateUp = navigateUp,
                                        titleDetail = title,
                                        onBookmarkClick = {
                                            viewModel.onEvent(
                                                AnimeDetailsEvent.UpsertDeleteAnime(
                                                    animeData.copy(
                                                        localId = id.hashCode().toString(),
                                                    ),
                                                ),
                                            )
                                        },
                                    )

                                    Row {
                                        Text(
                                            text = animeDataAttributes?.startDate?.split("-")
                                                ?.first()
                                                ?: "-",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium,
                                        )

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Star,
                                                contentDescription = null,
                                            )

                                            Text(
                                                text = animeDataAttributes?.averageRating ?: "0.0",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Medium,
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    details?.let { synopsis ->
                                        Column(
                                            modifier = Modifier
                                                .padding(horizontal = 20.dp, vertical = 10.dp)
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.Start,
                                        ) {
                                            Text(
                                                text = "Synopsis",
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Text(text = synopsis)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        when {
                            castingsState.isLoading -> ShimmerEffectCastings()

                            castingsState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = "Error: ${castingsState.errorMessage}")
                                }
                            }

                            castingsState.castingDataList != null -> {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    items(castingData) { cast ->
                                        CharactersItem(
                                            castingsData = cast,
                                            onClick = {
                                                navigator.navigateTo(
                                                    pane = ListDetailPaneScaffoldRole.Detail,
                                                    content = "${cast.attributes?.name}\n${cast.attributes?.description}",
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            detailPane = {
                val content = navigator.currentDestination?.content
                AnimatedPane {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding(),
                            )
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickableWithoutRipple { navigator.navigateBack() },
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "BackButton",
                        )

                        content?.let { desc ->

                            val splitContent = desc.toString().split("\n", limit = 2)
                            val name = splitContent.first()
                            val description = splitContent.last()

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 10.dp),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                text = name,
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                text = description,
                            )
                        }
                    }
                }
            },
        )
    }
}
