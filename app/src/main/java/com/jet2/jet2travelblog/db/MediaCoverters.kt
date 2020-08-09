package com.jet2.jet2travelblog.db

import androidx.room.TypeConverter
import com.example.android.codelabs.paging.model.Media
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class MediaCoverters {
    @TypeConverter
    fun toMedia(json: String): List<Media> {
        val type = object : TypeToken<List<Media>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<Media>): String {
        val type = object : TypeToken<List<Media>>() {}.type
        return Gson().toJson(torrent, type)
    }
}