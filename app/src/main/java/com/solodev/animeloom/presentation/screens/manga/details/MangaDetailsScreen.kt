package com.solodev.animeloom.presentation.screens.manga.details

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.presentation.common.DetailHeaderBar

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MangaDetailsScreen(
    id: String = "",
    localId: String = "",
    isFromBookmark: Boolean = false,
    coverImage: String,
    navigateUp: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val viewModel: MangaDetailsViewModel = hiltViewModel()
    val mangaState by viewModel.mangaDetailState.collectAsStateWithLifecycle()

    val mangaData = mangaState.mangaDataDetail ?: MangaData()

    if (viewModel.sideEffect != null) {
        Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
        viewModel.onEvent(MangaDetailsEvent.RemoveSideEffect)
    }

    LaunchedEffect(true) {
        viewModel.getMangaById(id.toInt())
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = innerPadding.calculateBottomPadding() + 10.dp
                )
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
                            }
                        )
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {

                when {
                    mangaState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    mangaState.errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error: ${mangaState.errorMessage}")
                        }
                    }

                    mangaState.mangaDataDetail != null -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))

                            DetailHeaderBar(
                                navigateUp = navigateUp,
                                titleDetail = mangaState.mangaDataDetail?.attributes?.canonicalTitle,
                                onBookmarkClick = {
                                    viewModel.onEvent(
                                        MangaDetailsEvent.UpsertDeleteManga(
                                            mangaData.copy(localId = id.hashCode().toString())
                                        )
                                    )
                                })

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Synopsis",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = mangaState.mangaDataDetail?.attributes?.synopsis ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}

