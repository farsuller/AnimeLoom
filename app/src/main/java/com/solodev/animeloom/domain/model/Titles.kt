package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Titles(

    @SerializedName("en")
    val en: String?

) : Parcelable