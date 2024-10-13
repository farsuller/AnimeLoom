package com.solodev.animeloom.presentation.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solodev.animeloom.R
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.utils.AnimesPreviews
import com.solodev.animeloom.presentation.screens.details.components.DetailTopBar
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.Constants.TestTags.DETAIL_DESCRIPTION
import com.solodev.animeloom.utils.Constants.TestTags.DETAIL_IMAGE
import com.solodev.animeloom.utils.Constants.TestTags.DETAIL_TITLE

@Composable
fun DetailScreen(
    animeData: AnimeData,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        DetailTopBar(
            onBrowsingClick = {

            },
            onShareClick = {

            },
            onBookmarkClick = {  },
            onBackClick = navigateUp,
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
            ),
        ) {
            item {
                Text(
                    modifier = Modifier.testTag(DETAIL_TITLE),
                    text = animeData.attributes.canonicalTitle ?: "Default Title",
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 30.sp,
                )

                Spacer(modifier = Modifier.height(14.dp))

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 320.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .testTag(DETAIL_IMAGE),
                    model = ImageRequest
                        .Builder(context)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .data(animeData.attributes.posterImage?.large)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "${animeData.attributes.episodeCount} ${animeData.attributes.episodeLength}",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = animeData.attributes.ageRating ?: "Default Genre",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = animeData.attributes.ageRatingGuide ?: "Default Rating",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    modifier = Modifier.testTag(DETAIL_DESCRIPTION),
                    text = animeData.attributes.description ?: "Default Description",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 25.sp,
                )
            }
        }
    }
}

