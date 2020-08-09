package com.example.android.codelabs.paging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey @SerializedName("id") var userId: Int,
    @SerializedName("blogId") val blogId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("city") val city: String,
    @SerializedName("designation") val designation: String,
    @SerializedName("about") val about: String
)