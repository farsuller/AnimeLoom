package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverImage(

    @SerializedName("tiny")
    val tiny: String?,

    @SerializedName("small")
    val small: String?,

    @SerializedName("large")
    val large: String?,

    @SerializedName("original")
    val original: String?
):Parcelable