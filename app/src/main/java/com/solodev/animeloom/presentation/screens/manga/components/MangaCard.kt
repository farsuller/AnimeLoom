package com.solodev.animeloom.presentation.screens.manga.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.presentation.common.AnimeCardShimmerEffect
import com.solodev.animeloom.utils.Constants
import com.solodev.animeloom.utils.toDp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MangaCard(
    modifier: Modifier = Modifier,
    mangaData: MangaData,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val image = mangaData.attributes?.posterImage?.original
    val imageHeight = mangaData.attributes?.posterImage?.meta?.dimensions?.small?.height ?: 402
    val imageWidth = mangaData.attributes?.posterImage?.meta?.dimensions?.small?.width ?: 284

    Card(
        onClick = onClick,
        modifier = Modifier
            .height(imageHeight.toDp())
            .width(imageWidth.toDp())
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {
        SubcomposeAsyncImage(
            model = image,
            contentDescription = mangaData.attributes?.canonicalTitle,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = mangaData.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500)
                    },
                )
                .height(imageHeight.toDp())
                .width(imageWidth.toDp())
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    AnimeCardShimmerEffect(height = imageHeight, width = imageWidth)
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)
                }
            },
        )
    }
}
