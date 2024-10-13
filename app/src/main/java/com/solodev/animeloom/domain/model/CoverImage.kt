package com.solodev.animeloom.domain.model

import com.google.gson.annotations.SerializedName


data class CoverImage(

    @SerializedName("tiny")
    val tiny: String?,

    @SerializedName("small")
    val small: String?,

    @SerializedName("large")
    val large: String?,

    @SerializedName("original")
    val original: String?
)