package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.solodev.animeloom.R
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.Attributes
import com.solodev.animeloom.domain.model.Dimensions
import com.solodev.animeloom.domain.model.Meta
import com.solodev.animeloom.domain.model.PosterImage
import com.solodev.animeloom.domain.model.Size
import com.solodev.animeloom.presentation.common.AnimeCardShimmerEffect
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.AnimesPreviews
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
        val image = animeData.attributes?.posterImage?.original
        val imageHeight = animeData.attributes?.posterImage?.meta?.dimensions?.small?.height ?: 402
        val imageWidth = animeData.attributes?.posterImage?.meta?.dimensions?.small?.width ?: 284

        AsyncImage(
            model = image,
            contentDescription = animeData.attributes?.canonicalTitle,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = animeData.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500)
                    }
                )
                .height(imageHeight.toDp())
                .width(imageWidth.toDp())
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
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
    ) {
        val image = animeData.attributes?.posterImage?.original
        val imageHeight = animeData.attributes?.posterImage?.meta?.dimensions?.medium?.height ?: 554
        val imageWidth = animeData.attributes?.posterImage?.meta?.dimensions?.medium?.width ?: 390

        SubcomposeAsyncImage(
            model = image,
            contentDescription = animeData.attributes?.canonicalTitle,
            modifier = Modifier
                .height(imageHeight.toDp())
                .width(imageWidth.toDp())
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    AnimeCardShimmerEffect(height = imageHeight, width = imageWidth)
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)
                }
            },
            error = {
                if (LocalInspectionMode.current) {
                    Image(
                        painter = painterResource(id = R.drawable.onboarding1),
                        contentDescription = ""
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Failed to Load Image",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center)
                    }
                }
            }
        )
    }
}

@AnimesPreviews
@Composable
fun AnimeCardPreview() {
    AnimeLoomTheme {
        Surface {
            AnimeCard(
                onClick = {},
                animeData = AnimeData(
                    attributes = Attributes(
                        posterImage = PosterImage(
                            meta = Meta(
                                dimensions = Dimensions(
                                    medium = Size(width = 390, height = 554)
                                )
                            ),
                            medium = "https://media.kitsu.app/anime/poster_images/7442/medium.jpg"
                        )
                    )
                )
            )
        }
    }
}


