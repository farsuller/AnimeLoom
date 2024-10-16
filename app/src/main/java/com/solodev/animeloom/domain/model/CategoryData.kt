package com.solodev.animeloom.domain.model

import com.solodev.animeloom.data.remote.dto.Relationships

data class CategoryData(
    val id : String,
    val type : String,
    val links: Links,
    val attributes : Attributes,
    val relationships: Relationships
)
