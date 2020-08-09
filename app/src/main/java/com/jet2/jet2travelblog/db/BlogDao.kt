package com.jet2.jet2travelblog.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.codelabs.paging.model.Article


@Dao
abstract class BlogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(articles: List<Article>)

    @Query(
        "SELECT * FROM blog " +
                "ORDER BY id ASC"
    )
    abstract fun getBlogs(): PagingSource<Int, Article>

    @Query("DELETE FROM blog")
    abstract suspend fun deleteBlogs()

}