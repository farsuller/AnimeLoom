package com.solodev.animeloom.data.remote.dto.response

import com.solodev.animeloom.data.remote.dto.MangaDataDto
import com.solodev.animeloom.domain.model.LinksX
import com.solodev.animeloom.domain.model.MetaC

data class MangaListResponse(
    val data : List<MangaDataDto>,
    val meta : MetaC,
    val links : LinksX
)



