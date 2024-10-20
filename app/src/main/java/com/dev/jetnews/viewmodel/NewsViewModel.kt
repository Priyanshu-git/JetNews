package com.dev.jetnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.jetnews.model.NewsResponse
import com.dev.jetnews.repository.NewsRepository
import com.dev.jetnews.repository.NewsResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _news = MutableStateFlow<NewsResource<NewsResponse>>(NewsResource.Loading())
    val news: StateFlow<NewsResource<NewsResponse>> get() = _news
    val repository = NewsRepository

    fun fetchNews(query: String = currentQuery, page: Int = currentPage) {
        viewModelScope.launch(Dispatchers.IO) {
            _news.value = NewsResource.Loading() // Set loading state
            try {
                val response = repository.getNews(query = query, page = page)

                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "ok") {
                            _news.value = NewsResource.Success(it)
                        } else {
                            _news.value = NewsResource.Error("Error: ${it.message}")
                        }
                    } ?: run {
                        _news.value = NewsResource.Error("No data found")
                    }
                } else {
                    _news.value = NewsResource.Error("Error: ${response.body()?.message}")
                }
            } catch (e: Exception) {
                _news.value = NewsResource.Error("Exception: ${e.message}")
            }
        }
    }

    var currentPage = 1
        set(value) {
            field = value
            fetchNews(query = "example", page = value)
        }

    var currentQuery = ""
        set(value) {
            field = value
            currentPage = 1
            fetchNews(query = value, page = 1)
        }
}