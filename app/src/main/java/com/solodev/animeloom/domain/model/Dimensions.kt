package com.solodev.animeloom.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dimensions(
    val large: Size? = null,
    val medium: Size? = null,
    val small: Size? = null,
    val tiny: Size? = null
):Parcelable