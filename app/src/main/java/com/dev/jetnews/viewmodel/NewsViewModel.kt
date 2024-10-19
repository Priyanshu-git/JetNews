package com.dev.jetnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.jetnews.BuildConfig
import com.dev.jetnews.model.NewsResponse
import com.dev.jetnews.repository.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _news = MutableStateFlow<NewsResponse?>(null)
    val news: StateFlow<NewsResponse?> get() = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getNews(
                query = "bitcoin",
                apiKey = BuildConfig.NEWS_API_KEY,
                pageSize = 10
            )
            if (response.isSuccessful) {
                _news.value = response.body()
            }
        }
    }
}