package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.utils.clickableWithoutRipple


class SeeAll(
    private val seeAllClicked: () -> Unit = {}
) : @Composable () -> Unit {


    @Composable
    override fun invoke() {
        Text(
            modifier = Modifier
                .padding(end = 8.dp, bottom = 10.dp)
                .clickableWithoutRipple { seeAllClicked() },
            text = "See All",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}