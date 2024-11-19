package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.CastingsData

data class CastingsDataDto(
    val attributes: AttributesDto,
    val id: String,
    val type: String,
) {
    fun toModel(): CastingsData =
        CastingsData(id = id, attributes = attributes.toModel(), type = type)
}
