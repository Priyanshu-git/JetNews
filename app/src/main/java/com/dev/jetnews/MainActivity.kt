package com.dev.jetnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.jetnews.model.ArticlesItem
import com.dev.jetnews.repository.NewsResource
import com.dev.jetnews.ui.theme.JetNewsTheme
import com.dev.jetnews.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetNewsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MainToolbar() }
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

        newsResponse.let { response ->
            when (response.value) {
                is NewsResource.Success -> {
                    val newsList = response.value.data?.articles
                    if (!newsList.isNullOrEmpty()) {
                        NewsList(newsList)
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

    @Composable
    private fun NewsList(newsList: List<ArticlesItem?>) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(newsList.size) { index ->
                Text(
                    text = "${newsList[index]?.title}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainToolbar() {
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = { newsViewModel.fetchNews("Sports") }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}


