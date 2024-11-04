package com.solodev.animeloom.presentation.screens.onboarding.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.theme.AnimeLoomTheme
import com.solodev.animeloom.theme.onSecondaryDark
import com.solodev.animeloom.utils.AnimesPreviews

@Composable
fun AnimeButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = onSecondaryDark,
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(size = 6.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
    }
}

@Composable
fun AnimeTextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White,
        )
    }
}

@AnimesPreviews
@Composable
private fun AnimeTextButtonPreview() {
    AnimeLoomTheme {
        Surface {
            AnimeTextButton(text = "Get Started", onClick = {})
        }
    }
}

@AnimesPreviews
@Composable
private fun AnimeButtonPreview() {
    AnimeLoomTheme {
        Surface {
            AnimeButton(text = "Get Started", onClick = {})
        }
    }
}
