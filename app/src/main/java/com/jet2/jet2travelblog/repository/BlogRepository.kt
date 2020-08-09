package com.jet2.jet2travelblog.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.api.BlogApi
import com.jet2.jet2travelblog.db.BlogDatabase
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class BlogRepository(
    private val service: BlogApi,
    private val database: BlogDatabase
) {

    /**
     * Exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getResultStream(): Flow<PagingData<Article>> {
        val pagingSourceFactory = { database.blogDao().getBlogs() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = BlogRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10//For this assignment
    }
}
