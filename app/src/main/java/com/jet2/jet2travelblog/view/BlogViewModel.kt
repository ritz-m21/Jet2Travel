package com.jet2.jet2travelblog.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.repository.BlogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class BlogViewModel(private val repository: BlogRepository) : ViewModel() {
    fun getBlogs(): Flow<PagingData<Article>> {
        val newResult: Flow<PagingData<Article>> = repository.getResultStream()
            .cachedIn(viewModelScope)
        return newResult
    }
}
