package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Attributes(

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("slug")
    val slug: String?,

    @SerializedName("synopsis")
    val synopsis: String?,

    @SerializedName("titles")
    val titles: Titles?,

    val title: String?,

    @SerializedName("description")
    val description : String?,

    @SerializedName("canonicalTitle")
    val canonicalTitle: String?,

    @SerializedName("abbreviatedTitles")
    val abbreviatedTitles: List<String>?,

    @SerializedName("averageRating")
    val averageRating: String?,

    @SerializedName("userCount")
    val userCount: Int?,

    @SerializedName("favoritesCount")
    val favoritesCount: Int?,

    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("popularityRank")
    val popularityRank: Int?,

    @SerializedName("ratingRank")
    val ratingRank: Int?,

    @SerializedName("ageRating")
    val ageRating: String?,

    @SerializedName("ageRatingGuide")
    val ageRatingGuide: String?,

    @SerializedName("subtype")
    val subtype: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("posterImage")
    val posterImage: PosterImage?,

    @SerializedName("coverImage")
    val coverImage: CoverImage?,

    @SerializedName("episodeCount")
    val episodeCount: Int?,

    @SerializedName("episodeLength")
    val episodeLength: Int?,

    @SerializedName("showType")
    val showType: String?,

    @SerializedName("totalMediaCount")
    val totalMediaCount: Int = 0,

    val nsfw: Boolean = false,

    @SerializedName("childCount")
    val childCount: Int = 0,

)