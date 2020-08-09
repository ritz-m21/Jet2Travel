package com.example.android.codelabs.paging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "blog")
data class Article(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("content") val content: String,
    @SerializedName("comments") val comments: Int,
    @SerializedName("likes") val likes: Int,

    @SerializedName("media")
    @Expose
    val media: List<Media>,

    @SerializedName("user")
    @Expose
    val user: List<User>
)
