package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.utils.Constants
import com.solodev.animeloom.utils.toDp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeCard(
    modifier: Modifier = Modifier,
    animeData: AnimeData,
    onClick: () -> Unit = {},
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {
        AsyncImage(
            model = animeData.attributes?.posterImage?.original,
            contentDescription = animeData.attributes?.canonicalTitle,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = animeData.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500)
                    }
                )
                .height(
                    animeData.attributes?.posterImage?.meta?.dimensions?.small?.height?.toDp()
                        ?: 0.dp
                )
                .width(
                    animeData.attributes?.posterImage?.meta?.dimensions?.small?.width?.toDp()
                        ?: 0.dp
                )
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

    }

}

@Composable
fun AnimeCard(
    modifier: Modifier = Modifier,
    animeData: AnimeData,
    onClick: () -> Unit = {},

) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {
        AsyncImage(
            model = animeData.attributes?.posterImage?.original,
            contentDescription = animeData.attributes?.canonicalTitle,
            modifier = Modifier
                .height(animeData.attributes?.posterImage?.meta?.dimensions?.medium?.height?.toDp()
                        ?: 0.dp
                )
                .width(animeData.attributes?.posterImage?.meta?.dimensions?.medium?.width?.toDp()
                        ?: 0.dp
                )
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )
    }
}


