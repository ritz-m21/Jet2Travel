package com.jet2.jet2travelblog.view

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class BlogLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<BlogLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: BlogLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BlogLoadStateViewHolder {
        return BlogLoadStateViewHolder.create(parent, retry)
    }
}
