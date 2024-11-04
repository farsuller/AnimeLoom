package com.solodev.animeloom.presentation.screens.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.theme.onSecondaryDark
import com.solodev.animeloom.theme.primaryDark
import com.solodev.animeloom.utils.AnimesPreviews
import com.solodev.animeloom.utils.Dimens.IndicatorSize

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = Color.White,
    unselectedColor: Color = onSecondaryDark,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(IndicatorSize)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor),
            )
        }
    }
}

@AnimesPreviews
@Composable
fun PagerIndicatorPreview() {
    AnimeLoomTheme {
        Surface {
            PagerIndicator(pagesSize = 3, selectedPage = 1)
        }
    }
}
