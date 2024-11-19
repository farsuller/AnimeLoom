package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.Image
import com.solodev.animeloom.domain.model.Meta

data class ImageDto(
    val large: String? = null,
    val meta: Meta? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null,
) {
    fun toModel(): Image =
        Image(tiny = tiny, small = small, large = large, original = original)
}
