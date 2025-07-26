package com.dev.jetnews

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dev.jetnews.repository.NewsResource
import com.dev.jetnews.ui.NewsList
import com.dev.jetnews.ui.theme.JetNewsTheme
import com.dev.jetnews.ui.toolbar.MainToolbar
import com.dev.jetnews.viewmodel.NewsViewModel

@Composable
fun NewsScreenComponent(newsViewModel: NewsViewModel) {
    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }

    JetNewsTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainToolbar(newsViewModel) }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                NewsScreenContent(newsViewModel)
            }
        }
    }
}

@Composable
private fun NewsScreenContent(newsViewModel: NewsViewModel) {
    val newsResponse = newsViewModel.news.collectAsState()
    val context = LocalContext.current

    when (val result = newsResponse.value) {
        is NewsResource.Success -> {
            val newsList = result.data?.articles
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
                text = "${result.message}",
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


