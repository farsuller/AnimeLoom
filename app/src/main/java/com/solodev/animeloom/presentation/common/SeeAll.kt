package com.solodev.animeloom.presentation.common

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.utils.clickableWithoutRipple


class SeeAll(private val text: String,
             private val headerText: String) : @Composable () -> Unit{


    @Composable
    override fun invoke() {
        val context = LocalContext.current

        Text(
            modifier = Modifier
                .padding(end = 8.dp, bottom = 10.dp)
                .clickableWithoutRipple { Toast.makeText(context, headerText, Toast.LENGTH_SHORT).show() },
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}