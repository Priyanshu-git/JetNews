package com.dev.jetnews.repository

import com.dev.jetnews.BuildConfig
import com.dev.jetnews.repository.retrofit.RetrofitInstance

object NewsRepository {
    private val retrofit = RetrofitInstance.api

    suspend fun getNews(query: String, page: Int) =
        retrofit.getNews(apiKey = BuildConfig.NEWS_API_KEY, query = query, page = page, pageSize = 20)

}