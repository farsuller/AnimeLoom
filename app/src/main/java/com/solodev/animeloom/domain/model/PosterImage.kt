package com.solodev.animeloom.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PosterImage(

    @SerializedName("tiny")
    val tiny: String?,

    @SerializedName("small")
    val small: String?,

    @SerializedName("medium")
    val medium: String?,

    @SerializedName("large")
    val large: String?,

    @SerializedName("original")
    val original: String?,

    @SerializedName("meta")
    val meta: Meta?


) : Parcelable