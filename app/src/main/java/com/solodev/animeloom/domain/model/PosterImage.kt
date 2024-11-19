package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PosterImage(

    @SerializedName("tiny")
    val tiny: String? = null,

    @SerializedName("small")
    val small: String? = null,

    @SerializedName("medium")
    val medium: String? = null,

    @SerializedName("large")
    val large: String? = null,

    @SerializedName("original")
    val original: String? = null,

    @SerializedName("meta")
    val meta: Meta? = null,

) : Parcelable
