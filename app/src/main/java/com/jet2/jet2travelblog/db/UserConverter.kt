package com.jet2.jet2travelblog.db

import androidx.room.TypeConverter
import com.example.android.codelabs.paging.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverter {
    @TypeConverter
    fun toUser(json: String): List<User> {
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<User>): String {
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().toJson(torrent, type)
    }
}