package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.solodev.animeloom.R
import com.solodev.animeloom.domain.model.Attributes
import com.solodev.animeloom.domain.model.CastingsData
import com.solodev.animeloom.domain.model.Dimensions
import com.solodev.animeloom.domain.model.Meta
import com.solodev.animeloom.domain.model.PosterImage
import com.solodev.animeloom.domain.model.Size
import com.solodev.animeloom.presentation.components.AnimeCardShimmerEffect
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.AnimesPreviews
import com.solodev.animeloom.utils.Constants

@Composable
fun CharactersItem(
    modifier: Modifier = Modifier,
    castingsData: CastingsData,
    onClick: () -> Unit = {},
) {
    val data = castingsData.attributes
    val image = data?.image?.original

    Column(
        modifier = Modifier.size(height = 140.dp, width = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = Constants.Elevation.level0),
        ) {
            SubcomposeAsyncImage(
                model = image,
                contentDescription = data?.canonicalTitle,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        AnimeCardShimmerEffect()
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)
                    }
                },
                error = {
                    if (LocalInspectionMode.current) {
                        Image(
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.onboarding1),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.error,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.padding(10.dp),
                                fontFamily = MaterialTheme.typography.labelSmall.fontFamily,
                                text = "Failed to Load Image",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                },
            )
        }

        Text(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 10.dp,
                    end = 10.dp,
                )
                .width(120.dp),
            text = data?.name ?: data?.canonicalName ?: "",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            lineHeight = 20.sp,
        )
    }
}

@AnimesPreviews
@Composable
fun CharactersItemPreview() {
    AnimeLoomTheme {
        Surface {
            CharactersItem(
                onClick = {},
                castingsData = CastingsData(
                    attributes = Attributes(
                        name = "Character Name",
                        posterImage = PosterImage(
                            meta = Meta(
                                dimensions = Dimensions(
                                    medium = Size(width = 390, height = 554),
                                ),
                            ),
                            medium = "https://media.kitsu.app/anime/poster_images/7442/medium.jpg",
                        ),
                    ),
                ),
            )
        }
    }
}
