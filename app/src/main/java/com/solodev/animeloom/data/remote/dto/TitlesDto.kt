package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.Titles

data class TitlesDto(
    val en: String? = null,
    val en_jp: String? = null,
    val ja_jp: String? = null
) {
    fun toModel(): Titles = Titles(en = en)
}