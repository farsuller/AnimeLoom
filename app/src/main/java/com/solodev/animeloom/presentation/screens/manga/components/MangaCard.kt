package com.solodev.animeloom.presentation.screens.manga.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.utils.Constants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MangaCard(
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
        Column(
            modifier = modifier.padding(all = 10.dp),
        ) {
            AsyncImage(
                model = mangaData.attributes.posterImage?.original,
                contentDescription = mangaData.attributes.canonicalTitle,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = mangaData.id),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 500)
                        }
                    )
                    .size(90.dp)
                    .clip(RoundedCornerShape(10.dp))
                ,
                contentScale = ContentScale.Crop
            )

            Text(
                text = mangaData.attributes.canonicalTitle ?: "Default Title",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

}


