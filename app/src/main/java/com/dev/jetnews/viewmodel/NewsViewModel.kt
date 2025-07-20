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

    private val _appBarTitle = MutableStateFlow("")
    val appBarTitle: StateFlow<String> get() = _appBarTitle
    fun setAppBarTitle(title: String) {
        _appBarTitle.value = title.trim()
    }

    private var endOfList = false

    enum class NewsMode { TOP_HEADLINES, SEARCH }

    var newsMode = NewsMode.TOP_HEADLINES

    var currentPage = 1
        set(value) {
            field = value
            if (value == 1)
                fetchNews()
            else {
                if (!endOfList)
                    fetchNewsScroll()
            }
        }

    var currentQuery = ""

    private suspend fun makeRequest(query: String=""): retrofit2.Response<NewsResponse> {
        endOfList = false
        return when(newsMode){
            NewsMode.TOP_HEADLINES -> repository.getTopHeadlines(page = currentPage)
            NewsMode.SEARCH -> repository.getNews(query = query, page = currentPage)
        }
    }

    private fun handleResponse(response: retrofit2.Response<NewsResponse>) {
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
    }

 private fun handleResponseScroll(response: retrofit2.Response<NewsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "ok") {
                    if (!it.articles.isNullOrEmpty()){
                        val currentNewsList = _news.value.data?.articles?.toMutableList() ?: mutableListOf()
                        currentNewsList.addAll(it.articles)
                        _news.value = NewsResource.Success(it.copy(articles = currentNewsList))
                    } else {
                        endOfList = true
                    }
                } else {
                    _news.value = NewsResource.Error("Error: ${it.message}")
                }
            } ?: run {
                _news.value = NewsResource.Error("No data found")
            }
        } else {
            _news.value = NewsResource.Error("Error: ${response.body()?.message}")
        }
    }

    fun fetchNews(query: String = currentQuery, page: Int = currentPage) {
        newsMode = NewsMode.SEARCH
        currentQuery = query
        viewModelScope.launch(Dispatchers.IO) {
            _news.value = NewsResource.Loading() // Set loading state
            try {
                val response = makeRequest(query)
                handleResponse(response)
            } catch (e: Exception) {
                _news.value = NewsResource.Error("Exception: ${e.message}")
            }
        }
    }

    fun fetchTopHeadlines(page: Int = currentPage) {
        newsMode = NewsMode.TOP_HEADLINES
        viewModelScope.launch(Dispatchers.IO) {
            _news.value = NewsResource.Loading() // Set loading state
            try {
                val response = makeRequest()
                handleResponse(response)
            } catch (e: Exception) {
                _news.value = NewsResource.Error("Exception: ${e.message}")
            }
        }
    }

    fun fetchNewsScroll(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = makeRequest(query = currentQuery)
                handleResponseScroll(response)
            } catch (e: Exception) {
                _news.value = NewsResource.Error("Exception: ${e.message}")
            }
        }
    }

    fun refresh() {
        _news.value = NewsResource.Loading()
        currentPage = 1
    }


}