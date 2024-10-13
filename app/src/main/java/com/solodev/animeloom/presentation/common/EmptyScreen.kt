package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.solodev.animeloom.R
import com.solodev.animeloom.utils.AnimesPreviews
import java.net.ConnectException
import java.net.SocketTimeoutException

//Reused Screen for Search Empty Screen and Bookmark Screen
@Composable
fun EmptyScreen(error: LoadState.Error? = null, isBookmarkScreen: Boolean = false) {
    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    if (error == null) {
        message = if(isBookmarkScreen) "You have not saved anime so far !"
        else "You have not searched any anime so far !"
        icon = R.drawable.ic_search_document
    }

    EmptyContent(message = message, iconId = icon)
}

@Composable
fun EmptyContent(message: String, iconId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(120.dp),
        )
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> "Internet Network Unavailable."
        is ConnectException -> "Network Unavailable."
        else -> "Network Unavailable."

    }
}

@AnimesPreviews
@Composable
fun EmptyScreenPreview() {
    EmptyContent(message = "Internet Unavailable.", R.drawable.ic_network_error)
}
