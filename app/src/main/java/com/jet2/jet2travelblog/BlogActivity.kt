package com.jet2.jet2travelblog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.jet2.jet2travelblog.databinding.ActivityBlogBinding
import com.jet2.jet2travelblog.di.Injection
import com.jet2.jet2travelblog.view.BlogAdapter
import com.jet2.jet2travelblog.view.BlogLoadStateAdapter
import com.jet2.jet2travelblog.view.BlogViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BlogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlogBinding
    private lateinit var viewModel: BlogViewModel
    private lateinit var adapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(BlogViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.blogList.addItemDecoration(decoration)

        initAdapter()

        //Begin
        lifecycleScope.launch {
            viewModel.getBlogs().collectLatest {
                adapter.submitData(it)
            }
        }

        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = BlogAdapter(glide)
        binding.blogList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = BlogLoadStateAdapter { adapter.retry() },
            footer = BlogLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.blogList.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Error ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}