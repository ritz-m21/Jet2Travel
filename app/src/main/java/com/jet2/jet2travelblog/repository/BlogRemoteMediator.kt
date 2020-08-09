package com.jet2.jet2travelblog.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction

import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.api.BlogApi
import com.jet2.jet2travelblog.db.BlogDatabase
import com.jet2.jet2travelblog.db.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

// BlogAPI is 1 based: https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/blogs?page=1&limit=10
private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class BlogRemoteMediator(
    private val service: BlogApi,
    private val blogDatabase: BlogDatabase
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    //throw InvalidObjectException("Remote key and the prevKey should not be null")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.nextKey
            }

        }

        try {
            val articles = service.getArticles(page, state.config.pageSize)

            val endOfPaginationReached = articles.isEmpty()
            blogDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    blogDatabase.remoteKeysDao().clearRemoteKeys()
                    blogDatabase.blogDao().deleteBlogs()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = articles.map {
                    RemoteKeys(blogId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                blogDatabase.remoteKeysDao().insertAll(keys)
                blogDatabase.blogDao().insertAll(articles)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                // Get the remote keys of the last item retrieved
                blogDatabase.remoteKeysDao().remoteKeysBlogId(article.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                // Get the remote keys of the first items retrieved
                blogDatabase.remoteKeysDao().remoteKeysBlogId(article.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { blogId ->
                blogDatabase.remoteKeysDao().remoteKeysBlogId(blogId)
            }
        }
    }

}