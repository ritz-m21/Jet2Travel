package com.jet2.jet2travelblog.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.jet2.jet2travelblog.R
import com.jet2.jet2travelblog.databinding.LoadstateFooterItemBinding

class BlogLoadStateViewHolder(
    private val binding: LoadstateFooterItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): BlogLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loadstate_footer_item, parent, false)
            val binding = LoadstateFooterItemBinding.bind(view)
            return BlogLoadStateViewHolder(binding, retry)
        }
    }
}
