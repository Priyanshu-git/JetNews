package com.dev.jetnews.repository.retrofit

import com.dev.jetnews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getNews(@Query("q") query: String, @Query("apiKey") apiKey: String, @Query("pageSize") pageSize: Int): Response<NewsResponse>
}
