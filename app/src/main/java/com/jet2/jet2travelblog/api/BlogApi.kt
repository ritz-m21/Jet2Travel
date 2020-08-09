package com.jet2.jet2travelblog.api

import com.example.android.codelabs.paging.model.Article
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api to retrieve blog articles
 */
interface BlogApi {
    @GET("jet2/api/v1/blogs")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int //10 always
    ): List<Article>

    companion object {
        private const val BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/"

        fun create(): BlogApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlogApi::class.java)
        }
    }
}