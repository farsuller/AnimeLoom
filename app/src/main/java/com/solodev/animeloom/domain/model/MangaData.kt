package com.solodev.animeloom.domain.model



data class MangaData(
    val id : String,
    val type : String,
    val links: Links,
    val attributes : Attributes,
    val relationships: RelationshipsX
)
