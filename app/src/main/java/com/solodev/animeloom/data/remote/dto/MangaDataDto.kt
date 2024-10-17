package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.CategoryData
import com.solodev.animeloom.domain.model.Links
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.model.Relation
import com.solodev.animeloom.domain.model.RelationshipsX


data class MangaDataDto(
    val id : String,
    val type : String,
    val links: Links,
    val attributes : AttributesDto,
    val relationships: RelationshipsX
){
    fun toModel() : MangaData = MangaData(
        id = id,
        type = type ,
        links = links,
        attributes = attributes.toModel(),
        relationships = relationships)
}



