package com.example.android.codelabs.paging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Media(
    @PrimaryKey @SerializedName("id") var mediaId: Int,
    @SerializedName("blogId") val blogId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)