package com.solodev.animeloom.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import com.solodev.animeloom.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Get the Latest Animes",
        description = "New Animes, TV Shows, and Updates",
        image = R.drawable.onboarding1,
    ),
    Page(
        title = "Discover and Read",
        description = "Explore Timely Stories from Across the Globe",
        image = R.drawable.onboarding2,
    ),
    Page(
        title = "Animes & Manga ",
        description = "Tailored to Suit Your Interests",
        image = R.drawable.onboarding3,
    ),
)
