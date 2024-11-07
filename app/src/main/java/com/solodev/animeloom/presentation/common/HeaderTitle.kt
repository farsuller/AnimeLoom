package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


class HeaderTitle(private val text: String) : @Composable () -> Unit{
    @Composable
    override fun invoke() {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 10.dp),
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}