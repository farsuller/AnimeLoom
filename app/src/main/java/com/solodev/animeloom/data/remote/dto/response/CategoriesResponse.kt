package com.solodev.animeloom.data.remote.dto.response

import com.solodev.animeloom.data.remote.dto.CategoryDataDto

data class CategoriesResponse(
    val data : List<CategoryDataDto>,
    val meta : Meta,
    val links : Links
)

data class Meta(
    val count : Int,
)

data class Links(
    val first : String,
    val last : String,
    val next : String,
)