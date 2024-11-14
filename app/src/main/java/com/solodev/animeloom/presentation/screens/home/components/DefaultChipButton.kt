package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.solodev.animeloom.theme.surfaceLight


@Composable
fun DefaultChipButton(
    modifier: Modifier = Modifier,
    text: String = "",
    isSelected: Boolean = false,
    onClickedCategory: () -> Unit = {},
) {
    FilterChip(
        onClick = { onClickedCategory() },
        label = {
            Text(text)
        },
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = surfaceLight,
            selectedContainerColor = MaterialTheme.colorScheme.onSurface
        ),

        border = FilterChipDefaults.filterChipBorder(
            enabled = isSelected,
            selected = isSelected,
            disabledBorderColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}

