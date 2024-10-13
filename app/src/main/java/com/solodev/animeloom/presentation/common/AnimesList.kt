package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.data.remote.dto.AnimeDataDto
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.AnimesPreviews

@Composable
fun AnimeList(
    modifier: Modifier = Modifier,
    animeData: List<AnimeDataDto>,
    onClick: (AnimeData) -> Unit,
) {



}


@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10) {
            AnimeCardShimmerEffect(
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectListPreview() {
    AnimeLoomTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ShimmerEffect()
        }
    }
}
