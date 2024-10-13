package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.Links
import com.solodev.animeloom.domain.model.Relationships

data class AnimeDataDto(
    val attributes: AttributesDto,
    val id: String,
    val links: Links,
    val relationships: Relationships,
    val type: String
) {
    fun toModel(): AnimeData = AnimeData(id = id, attributes = attributes.toModel())
}