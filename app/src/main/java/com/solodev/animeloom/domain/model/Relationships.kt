package com.solodev.animeloom.domain.model

data class Relationships(
    val categories: Relation? = null,
    val chapters: Relation? = null,
    val genres: Relation? = null,
    val installments: Relation? = null,
    val mangaCharacters: Relation? = null,
    val mangaStaff: Relation? = null,
    val mappings: Relation? = null,
    val mediaRelationships: Relation? = null,
    val reviews: Relation? = null
)