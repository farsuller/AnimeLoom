package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeaderTitle(text : String= "", modifier : Modifier = Modifier){
    Text(
        modifier = modifier
            .padding(start = 8.dp, bottom = 10.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
    )
}