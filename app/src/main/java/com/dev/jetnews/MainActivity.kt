package com.dev.jetnews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dev.jetnews.repository.NewsResource
import com.dev.jetnews.ui.NewsList
import com.dev.jetnews.ui.theme.JetNewsTheme
import com.dev.jetnews.ui.toolbar.MainToolbar
import com.dev.jetnews.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        newsViewModel.fetchNews(query = getString(R.string.default_search))
        setContent {
            JetNewsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MainToolbar(newsViewModel) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        content = { NewsScreen() }
                    )
                }
            }
        }
    }

    @Composable
    fun NewsScreen() {
        val newsResponse = newsViewModel.news.collectAsState()
        val context = LocalContext.current

        newsResponse.let { response ->
            when (response.value) {
                is NewsResource.Success -> {
                    val newsList = response.value.data?.articles
                    if (!newsList.isNullOrEmpty()) {
                        Toast.makeText(context, "${newsList.size} articles found", Toast.LENGTH_SHORT).show()
                        NewsList(newsList, newsViewModel)
                    } else {
                        Text(
                            text = "No news found",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                is NewsResource.Error -> {
                    Text(
                        text = "${response.value.message}",
                        modifier = Modifier.padding(8.dp)
                    )
                }

                is NewsResource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}


