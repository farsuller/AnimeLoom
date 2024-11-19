package com.solodev.animeloom.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solodev.animeloom.domain.model.Attributes
import com.solodev.animeloom.domain.model.CoverImage
import com.solodev.animeloom.domain.model.PosterImage
import com.solodev.animeloom.domain.model.Titles

@ProvidedTypeConverter
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromAttributes(attributes: Attributes?): String? {
        return gson.toJson(attributes)
    }

    @TypeConverter
    fun toAttributes(data: String?): Attributes? {
        val type = object : TypeToken<Attributes>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromTitles(titles: Titles?): String? {
        return gson.toJson(titles)
    }

    @TypeConverter
    fun toTitles(data: String?): Titles? {
        val type = object : TypeToken<Titles>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromPosterImage(posterImage: PosterImage?): String? {
        return gson.toJson(posterImage)
    }

    @TypeConverter
    fun toPosterImage(data: String?): PosterImage? {
        val type = object : TypeToken<PosterImage>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromCoverImage(coverImage: CoverImage?): String? {
        return gson.toJson(coverImage)
    }

    @TypeConverter
    fun toCoverImage(data: String?): CoverImage? {
        val type = object : TypeToken<CoverImage>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromAbbreviatedTitles(abbreviatedTitles: List<String>?): String? {
        return gson.toJson(abbreviatedTitles)
    }

    @TypeConverter
    fun toAbbreviatedTitles(data: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, type)
    }
}
