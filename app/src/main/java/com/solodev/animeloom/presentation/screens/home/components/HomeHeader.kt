package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solodev.animeloom.R
import com.solodev.animeloom.presentation.common.HeaderShimmerEffect

@Composable
fun HomeHeader(
    modifier: Modifier,
    animePosterHeader: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .matchParentSize(),
            model = animePosterHeader,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = animePosterHeader,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Transparent,
                        ),
                    )
                )
        )
    }
}