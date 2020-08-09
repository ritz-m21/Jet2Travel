package com.jet2.jet2travelblog.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
        @PrimaryKey val blogId: Long,
        val prevKey: Int?,
        val nextKey: Int?
)
