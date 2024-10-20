package com.dev.jetnews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.jetnews.model.ArticlesItem
import com.dev.jetnews.repository.NewsResource
import com.dev.jetnews.ui.NewsCard
import com.dev.jetnews.ui.theme.JetNewsTheme
import com.dev.jetnews.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        newsViewModel.fetchNews(baseContext.getString(R.string.default_search))
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
        val context = LocalContext.current

        newsResponse.let { response ->
            when (response.value) {
                is NewsResource.Success -> {
                    val newsList = response.value.data?.articles
                    if (!newsList.isNullOrEmpty()) {
                        Toast.makeText(context, "${newsList.size} articles found", Toast.LENGTH_SHORT).show()
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
    fun NewsList(newsList: List<ArticlesItem?>) {
        val listState = rememberLazyListState()
        val context = LocalContext.current

        // observe list scrolling
        val reachedBottom: Boolean by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount -1
            }
        }

        // load more if scrolled to bottom
        LaunchedEffect(reachedBottom) {
            if (reachedBottom) {
                newsViewModel.currentPage++
                Toast.makeText(context, "Page ${newsViewModel.currentPage}", Toast.LENGTH_SHORT).show()
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(newsList.size) { index ->
                NewsCard(news = newsList[index]!!)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainToolbar() {
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        var isSearchExpanded by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        val defaultSearch = stringResource(id = R.string.default_search)
        val focusRequester = remember { FocusRequester() }

        TopAppBar(
            title = {
                if (isSearchExpanded) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            isSearchExpanded = false
                            focusManager.clearFocus()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Cancel search")
                        }
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(text = "Search...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .padding(top = 2.dp, bottom = 8.dp)
                                .fillMaxHeight(0.8f)
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    newsViewModel.fetchNews(searchQuery.text)
                                    focusManager.clearFocus()
                                }
                            ),
                            singleLine = true
                        )
                    }
                } else {
                    Text(stringResource(id = R.string.app_name))
                }
            },
            actions = {
                if (isSearchExpanded) {
                    IconButton(onClick = {
                        newsViewModel.fetchNews(searchQuery.text)
                        focusManager.clearFocus()
                        isSearchExpanded = false
                    }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                } else {
                    IconButton(onClick = { isSearchExpanded = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Expand Search")
                    }
                    IconButton(onClick = {
                        newsViewModel.fetchNews(searchQuery.text.ifEmpty { defaultSearch })
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        // Request focus when search mode is expanded
        LaunchedEffect(isSearchExpanded) {
            if (isSearchExpanded) {
                focusRequester.requestFocus()
            }
        }
    }

    @Composable
    @Preview
    fun MainToolbarPreview() {
        MainToolbar()
    }
}


