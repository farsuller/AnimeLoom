package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HeaderBar(headerTitle: HeaderTitle? = null, seeAll: SeeAll? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        if (headerTitle != null) {
            headerTitle()
        }
        if (seeAll != null) {
            seeAll()
        }
    }
}
