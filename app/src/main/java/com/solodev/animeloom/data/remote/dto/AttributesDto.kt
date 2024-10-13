package com.solodev.animeloom.data.remote.dto

import com.solodev.animeloom.domain.model.Attributes

data class AttributesDto(
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val slug: String? = null,
    val synopsis: String? = null,
    val description: String? = null,
    val coverImageTopOffset: Int? = null,
    val titles: TitlesDto? = null,
    val canonicalTitle: String? = null,
    val abbreviatedTitles: List<String>? = null,
    val averageRating: String? = null,
    val ratingFrequencies: Map<String, String>? = null,
    val userCount: Int? = null,
    val favoritesCount: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val popularityRank: Int? = null,
    val ratingRank: Int? = null,
    val ageRating: String? = null,
    val ageRatingGuide: String? = null,
    val subtype: String? = null,
    val status: String? = null,
    val posterImage: PosterImageDto? = null,
    val coverImage: CoverImageDto? = null,
    val episodeCount: Int? = null,
    val episodeLength: Int? = null,
    val youtubeVideoId: String? = null,
    val showType: String? = null,
    val nsfw: Boolean? = null
) {
    fun toModel(): Attributes =
        Attributes(
            createdAt = createdAt,
            updatedAt = updatedAt,
            slug = slug,
            synopsis = synopsis,
            titles = titles?.toModel(),
            description = description,
            canonicalTitle = canonicalTitle,
            abbreviatedTitles = abbreviatedTitles,
            ageRating = ageRatingGuide,
            coverImage = coverImage?.toModel(),
            subtype = subtype,
            posterImage = posterImage?.toModel(),
            episodeCount = episodeCount,
            episodeLength = episodeLength,
            showType = showType,
            ageRatingGuide = ageRatingGuide,
            favoritesCount = favoritesCount,
            popularityRank = popularityRank,
            status = status,
            endDate = endDate,
            startDate = startDate,
            userCount = userCount,
            averageRating = averageRating,
            ratingRank = ratingRank
        )
}