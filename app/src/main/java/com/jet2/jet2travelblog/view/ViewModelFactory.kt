package com.jet2.jet2travelblog.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jet2.jet2travelblog.repository.BlogRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: BlogRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
