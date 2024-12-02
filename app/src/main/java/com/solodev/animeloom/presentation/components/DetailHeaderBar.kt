package com.solodev.animeloom.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solodev.animeloom.utils.clickableWithoutRipple

@Composable
fun DetailHeaderBar(
    navigateUp: () -> Unit,
    titleDetail: String?,
    onBookmarkClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            modifier = Modifier.clickableWithoutRipple { navigateUp() },
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = "BackButton",
        )

        Text(
            modifier = Modifier.width(300.dp),
            text = titleDetail ?: "Default Title",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
        )

        Icon(
            modifier = Modifier.clickableWithoutRipple { onBookmarkClick() },
            imageVector = Icons.Outlined.Bookmark,
            contentDescription = "BookmarkButton",
        )
    }
}
