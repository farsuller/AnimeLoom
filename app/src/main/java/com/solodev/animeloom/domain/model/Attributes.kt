package com.solodev.animeloom.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attributes(

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("slug")
    val slug: String? = null,

    @SerializedName("synopsis")
    val synopsis: String? = null,

    @SerializedName("titles")
    val titles: Titles? = null,

    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("canonicalTitle")
    val canonicalTitle: String? = null,

    @SerializedName("abbreviatedTitles")
    val abbreviatedTitles: List<String>? = null,

    @SerializedName("averageRating")
    val averageRating: String? = null,

    @SerializedName("userCount")
    val userCount: Int? = null,

    @SerializedName("favoritesCount")
    val favoritesCount: Int? = null,

    @SerializedName("startDate")
    val startDate: String? = null,

    @SerializedName("endDate")
    val endDate: String? = null,

    @SerializedName("popularityRank")
    val popularityRank: Int? = null,

    @SerializedName("ratingRank")
    val ratingRank: Int? = null,

    @SerializedName("ageRating")
    val ageRating: String? = null,

    @SerializedName("ageRatingGuide")
    val ageRatingGuide: String? = null,

    @SerializedName("subtype")
    val subtype: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("posterImage")
    val posterImage: PosterImage? = null,

    @SerializedName("coverImage")
    val coverImage: CoverImage? = null,

    @SerializedName("episodeCount")
    val episodeCount: Int? = null,

    @SerializedName("episodeLength")
    val episodeLength: Int? = null,

    @SerializedName("showType")
    val showType: String? = null,

    @SerializedName("totalMediaCount")
    val totalMediaCount: Int? = null,

    val nsfw: Boolean? = null,

    @SerializedName("childCount")
    val childCount: Int? = null,

    @SerializedName("names")
    val names: Names? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("canonicalName")
    val canonicalName: String? = null,

    @SerializedName("otherNames")
    val otherNames: List<String>? = null,

    @SerializedName("image")
    val image: Image? = null,

    ) : Parcelable