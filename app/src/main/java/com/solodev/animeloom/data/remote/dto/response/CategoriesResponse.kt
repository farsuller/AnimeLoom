package com.solodev.animeloom.data.remote.dto.response

import com.solodev.animeloom.data.remote.dto.CategoryDataDto
import com.solodev.animeloom.domain.model.LinksX
import com.solodev.animeloom.domain.model.MetaC

data class CategoriesResponse(
    val data: List<CategoryDataDto>,
    val meta: MetaC,
    val links: LinksX,
)
