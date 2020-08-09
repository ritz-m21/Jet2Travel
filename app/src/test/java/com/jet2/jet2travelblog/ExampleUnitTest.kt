package com.jet2.jet2travelblog

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.android.codelabs.paging.model.Article
import com.jet2.jet2travelblog.api.BlogApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBlogApiPaging1() = runBlocking {
        val myObjectUnderTest = BlogApi.create()
        val result: List<Article> = myObjectUnderTest.getArticles(1, 15)
        assertEquals(15, result.size)
    }

    @Test
    fun testBlogApiPaging2() = runBlocking {
        val myObjectUnderTest = BlogApi.create()
        val result: List<Article> = myObjectUnderTest.getArticles(1, 10)
        assertEquals(10, result.size)
    }

    @Test
    fun testBlogApiPaging3() = runBlocking {
        val myObjectUnderTest = BlogApi.create()
        val result: List<Article> = myObjectUnderTest.getArticles(0, 15)
        assertEquals(0, result.size)
    }


}