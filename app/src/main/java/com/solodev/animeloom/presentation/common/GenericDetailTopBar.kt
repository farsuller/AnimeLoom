package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.solodev.animeloom.utils.AnimesPreviews
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.utils.Constants.TestTags.DETAIL_TOP_BAR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDetailTopBar(
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(DETAIL_TOP_BAR),
        title = {},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "BackButton",
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = "BookmarkButton",
                )
            }
        },
    )
}

@AnimesPreviews
@Composable
internal fun DetailTopBarPreview() {
    AnimeLoomTheme {
        Surface {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                GenericDetailTopBar(
                    onBookmarkClick = {},
                    onBackClick = {},
                )
            }
        }

    }
}
