package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeAnimeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    animePosterHeader: String? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            AsyncImage(
                model = animePosterHeader,
                contentDescription = animePosterHeader,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        },
        title = {},
        actions = {},
    )
}