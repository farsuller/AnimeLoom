package com.solodev.animeloom.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Size(
    val width: Int? = null,
    val height: Int? = null
):Parcelable