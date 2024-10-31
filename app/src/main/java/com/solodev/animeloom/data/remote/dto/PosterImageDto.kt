package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.Meta
import com.solodev.animeloom.domain.model.PosterImage

data class PosterImageDto(
    val large: String? = null,
    val medium: String? = null,
    val meta: Meta? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null
) {
    fun toModel(): PosterImage =
        PosterImage(tiny = tiny, small = small, medium = medium, large = large, original = original, meta = meta)
}