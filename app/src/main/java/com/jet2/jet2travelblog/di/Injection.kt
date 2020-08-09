package com.jet2.jet2travelblog.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.jet2.jet2travelblog.api.BlogApi
import com.jet2.jet2travelblog.db.BlogDatabase
import com.jet2.jet2travelblog.repository.BlogRepository
import com.jet2.jet2travelblog.view.ViewModelFactory

/**
 * To enable dependency injection
 */
object Injection {

    /**
     * Provides repository
     */
    private fun provideRepository(context: Context): BlogRepository {
        return BlogRepository(BlogApi.create(), BlogDatabase.getInstance(context))
    }

    /**
     * Provides ViewModel factory for viewmodel
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideRepository(
                context
            )
        )
    }
}
