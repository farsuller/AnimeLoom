package com.solodev.animeloom.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "manga_data")
data class MangaData(

    @PrimaryKey
    @SerializedName("id")
    val id : String = "",

    @SerializedName("type")
    val type : String = "",

    @SerializedName("attributes")
    val attributes : Attributes? = null,
):Parcelable
