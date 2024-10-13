package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import coil.request.ImageRequest
import com.solodev.animeloom.data.remote.dto.AnimeDataDto
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.utils.Constants

@Composable
fun AnimeCard(
    modifier: Modifier = Modifier,
    animeData: AnimeDataDto,
    onClick: () -> Unit,
) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {
        Row(
            modifier = modifier
                .padding(all = 10.dp)
                .clickable {
                    onClick()
                },
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(height = 130.dp, width = 90.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest
                    .Builder(context)
                    .data(animeData.attributes.posterImage?.large)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),

                ) {
                Text(
                    text = animeData.attributes.canonicalTitle ?: "Default Title",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "${animeData.attributes.episodeCount} ${animeData.attributes.episodeLength}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = animeData.attributes.ageRating ?: "Default Genre",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = animeData.attributes.ageRatingGuide ?: "Default Rating",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = animeData.attributes.description ?: "Default Description",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}


