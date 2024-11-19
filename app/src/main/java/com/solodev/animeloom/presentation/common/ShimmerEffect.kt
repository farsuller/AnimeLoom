package com.solodev.animeloom.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.AnimesPreviews
import com.solodev.animeloom.utils.toDp

@Composable
fun ShimmerEffectDetailColumn() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .height(142.toDp())
                    .width(144.toDp())
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )

            Box(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .height(182.toDp())
                    .width(594.toDp())
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )

            Box(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .height(142.toDp())
                    .width(144.toDp())
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )
        }

        Spacer(modifier = Modifier.height(26.dp))
        Box(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .height(682.toDp())
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)
        }
    }
}

@Composable
fun ShimmerEffectVerticalGrid() {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) {
            AnimeCardShimmerEffect(
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp),
            )
        }
    }
}

@Composable
fun ShimmerEffectCarouselWithHeader() {
    Column {
        Box(
            modifier = Modifier
                .padding(all = 4.dp)
                .height(102.toDp())
                .width(494.toDp())
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(10) {
                AnimeCardShimmerEffect(
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp),
                )
            }
        }
    }
}

@Composable
fun ShimmerEffectCastings() {
    LazyRow(
        modifier = Modifier.padding(start = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(10) {
            CastingsCardShimmerEffect()
        }
    }
}

@Composable
fun ShimmerEffectCategoryCarousel() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(10) {
            CategoryShimmerEffect(
                modifier = Modifier.padding(horizontal = 24.dp),
            )
        }
    }
}

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "",
    ).value
    background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha))
}

@Composable
fun AnimeCardShimmerEffect(modifier: Modifier = Modifier, height: Int = 402, width: Int = 284) {
    Box(
        modifier = modifier
            .height(height.toDp())
            .width(width.toDp())
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect(),
    )
}

@Composable
fun CastingsCardShimmerEffect(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .shimmerEffect(),
    )
}

@Composable
fun HeaderShimmerEffect(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(360.dp)
            .width(500.dp)
            .shimmerEffect(),
    )
}

@Composable
fun CategoryShimmerEffect(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(all = 4.dp)
            .height(82.toDp())
            .width(194.toDp())
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect(),
    )
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectCastingsPreview() {
    AnimeLoomTheme {
        Surface {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                ShimmerEffectCastings()
            }
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectDetailColumnPreview() {
    AnimeLoomTheme {
        Surface {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                ShimmerEffectDetailColumn()
            }
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectVerticalGridPreview() {
    AnimeLoomTheme {
        Surface {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                ShimmerEffectVerticalGrid()
            }
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectPreview() {
    AnimeLoomTheme {
        Surface {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                AnimeCardShimmerEffect()
            }
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectListPreview() {
    AnimeLoomTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ShimmerEffectCarouselWithHeader()
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectCategoryPreview() {
    AnimeLoomTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            ShimmerEffectCategoryCarousel()
        }
    }
}

@AnimesPreviews
@Composable
internal fun ShimmerEffectHeaderPreview() {
    AnimeLoomTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            HeaderShimmerEffect()
        }
    }
}
