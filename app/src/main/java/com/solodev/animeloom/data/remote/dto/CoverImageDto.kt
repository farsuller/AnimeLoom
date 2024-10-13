package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.CoverImage
import com.solodev.animeloom.domain.model.Meta

data class CoverImageDto(
    val large: String? = null,
    val meta: Meta? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null
) {
    fun toModel(): CoverImage =
        CoverImage(tiny = tiny, small = small, large = large, original = original)
}