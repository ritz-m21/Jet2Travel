package com.jet2.jet2travelblog

import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.api.BlogApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBlogApiPaging() = runBlocking {
        val myObjectUnderTest = BlogApi.create()
        val result: List<Article> = myObjectUnderTest.getArticles(1, 15)
        assertEquals(15, result.size)
    }
}