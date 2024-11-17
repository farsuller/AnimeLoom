package com.solodev.animeloom.domain.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class CastingsData(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("attributes")
    val attributes: Attributes? = null,

    @SerializedName("type")
    val type : String = ""
)
