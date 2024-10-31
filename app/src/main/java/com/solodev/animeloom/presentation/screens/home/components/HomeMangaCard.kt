package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.utils.Constants
import com.solodev.animeloom.utils.toDp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeMangaCard(
    modifier: Modifier = Modifier,
    mangaData: MangaData,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {

        AsyncImage(
            model = mangaData.attributes?.posterImage?.original,
            contentDescription = mangaData.attributes?.canonicalTitle,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = mangaData.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500)
                    }
                )
                .height(mangaData.attributes?.posterImage?.meta?.dimensions?.small?.height?.toDp()
                        ?: 0.dp)
                .width(mangaData.attributes?.posterImage?.meta?.dimensions?.small?.width?.toDp()
                        ?: 0.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

    }

}


