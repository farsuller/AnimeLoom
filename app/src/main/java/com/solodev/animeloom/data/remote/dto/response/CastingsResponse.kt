package com.solodev.animeloom.data.remote.dto.response

import com.solodev.animeloom.data.remote.dto.CastingsDataDto

data class CastingsResponse(
    val included: List<CastingsDataDto>
)
