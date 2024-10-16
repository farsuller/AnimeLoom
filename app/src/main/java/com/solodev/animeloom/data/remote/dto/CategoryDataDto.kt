package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.CategoryData
import com.solodev.animeloom.domain.model.Links
import com.solodev.animeloom.domain.model.Relation


data class CategoryDataDto(
    val id : String,
    val type : String,
    val links: Links,
    val attributes : AttributesDto,
    val relationships: Relationships
){
    fun toModel() : CategoryData = CategoryData(
        id = id,
        type = type ,
        links = links,
        attributes = attributes.toModel(),
        relationships = relationships)
}


data class Relationships(
    val parent : Relation,
    val anime : Relation,
    val drama : Relation,
    val manga : Relation
)
