package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Names(

    @SerializedName("en")
    val en: String,

    @SerializedName("ja_jp")
    val jaJp: String,

) : Parcelable
