package com.jet2.jet2travelblog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.codelabs.paging.model.Article

@Database(
    entities = [Article::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MediaCoverters::class, UserConverter::class)//Converters for Media and User Object.
abstract class BlogDatabase : RoomDatabase() {

    abstract fun blogDao(): BlogDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: BlogDatabase? = null

        fun getInstance(context: Context): BlogDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BlogDatabase::class.java, "blog.db"
            )
                .build()
    }
}
