package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.solodev.animeloom.domain.model.AnimeData

@Composable
fun HomeHeader(
    modifier: Modifier,
    animeData: AnimeData? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.matchParentSize(),
            model = animeData?.attributes?.posterImage?.original,
            contentDescription = animeData?.attributes?.title,
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 1f),
                        ),
                    )
                )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = animeData?.attributes?.canonicalTitle ?: "",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 50.sp),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

    }
}