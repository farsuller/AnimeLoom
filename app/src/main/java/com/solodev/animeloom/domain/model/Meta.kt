package com.solodev.animeloom.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    val dimensions: Dimensions? = null,
) : Parcelable
