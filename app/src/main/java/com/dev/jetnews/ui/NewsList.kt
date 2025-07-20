package com.dev.jetnews.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dev.jetnews.model.ArticlesItem
import com.dev.jetnews.viewmodel.NewsViewModel

@Composable
fun NewsList(newsList: List<ArticlesItem?>, newsViewModel: NewsViewModel) {
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