package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.solodev.animeloom.theme.surfaceLight


@Composable
fun DefaultChipButton(
    modifier: Modifier = Modifier,
    text: String = "",
    isSelected: Boolean = false,
    onSelectionChanged: () -> Unit = {},
) {
    FilterChip(
        onClick = onSelectionChanged,
        label = {
            Text(text)
        },
        selected = isSelected,
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Check icon",
                    tint = Color.White,
                    modifier = modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
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

