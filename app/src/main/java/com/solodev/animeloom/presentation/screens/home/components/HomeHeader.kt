package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HomeHeader(
    modifier: Modifier,
    animePosterHeader: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = animePosterHeader,
            contentDescription = animePosterHeader,
            modifier = Modifier
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}