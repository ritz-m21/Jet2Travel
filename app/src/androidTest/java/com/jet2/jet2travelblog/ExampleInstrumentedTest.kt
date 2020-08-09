package com.jet2.jet2travelblog

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jet2.jet2travelblog", appContext.packageName)
    }

//    @Test
//    fun testPager() = runBlocking {
//        val myObjectUnderTest = BlogApi.create()
//        val repository = BlogRepository(myObjectUnderTest, BlogDatabase.getInstance(InstrumentationRegistry.getInstrumentation().targetContext))
//        val result: Flow<PagingData<Article>> = repository.getResultStream()
//    }

    @ExperimentalTime
    @Test
    fun testDate() {
        assertEquals("4", Utility.getDiff("2020-04-17T01:30:57.213Z"))
    }
}