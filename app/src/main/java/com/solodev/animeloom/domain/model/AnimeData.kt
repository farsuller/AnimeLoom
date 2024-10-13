package com.solodev.animeloom.domain.model

import com.google.gson.annotations.SerializedName



data class AnimeData(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("attributes")
    val attributes: Attributes,
)









