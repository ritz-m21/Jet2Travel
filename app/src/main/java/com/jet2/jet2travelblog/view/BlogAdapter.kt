package com.jet2.jet2travelblog.view

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.GlideRequests

/**
 * Adapter for the list of articles.
 */
class BlogAdapter(private val glide: GlideRequests) :
    PagingDataAdapter<Article, ViewHolder>(UserComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return BlogViewHolder.create(parent, glide)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as BlogViewHolder).bind(item)
        }
    }
}


object UserComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
