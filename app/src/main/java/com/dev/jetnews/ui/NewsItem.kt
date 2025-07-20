package com.dev.jetnews.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.jetnews.R
import com.dev.jetnews.model.ArticlesItem
import com.dev.jetnews.model.Source
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun NewsCard(news: ArticlesItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 0.dp)
            .fillMaxWidth()
            .clickable(true, onClick = {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("url", "${news.url?.toUri()}")
                context.startActivity(intent)
            }),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),

    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Text(
                text = "${news.title}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            )

            Text(
                text = "${news.source?.name}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp)
                    .background(
                        MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(25)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Text(
                text = "${news.description}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            ) {
                Subtitle(text = news.author)
                Subtitle(text = getFormattedDate(news.publishedAt))
            }

        }
    }
}

@Composable
fun Subtitle(text: String?) {
    Text(
        text = "$text",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

fun getFormattedDate(sourceDate: String?): String {
    if (sourceDate.isNullOrEmpty()) return ""
    val utcTimeZone = TimeZone.getTimeZone("UTC")

// Parse the input date string
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).apply {
        timeZone = utcTimeZone
    }
    val date = inputFormat.parse(sourceDate)

// Format the parsed date to the desired format
    val outputFormat = SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH).apply {
        timeZone = utcTimeZone
    }
    return "${date?.let { outputFormat.format(it) }}"
}

@Composable
@Preview
fun NewsCardPreview() {
    val sampleNews = ArticlesItem(
        source = Source("Wired", "wired"),
        author = "Joel Khalili",
        title = "Unmasking Bitcoin Creator Satoshi Nakamoto—Again",
        description = "A new HBO documentary takes a swing at uncovering the real identity of Satoshi Nakamoto, inventor of Bitcoin. But without incontrovertible proof, the myth lives on.",
        url = "https://www.wired.com/story/unmasking-bitcoin-creator-satoshi-nakamoto-again/",
        urlToImage = "https://media.wired.com/photos/6703eb3979f13fda7f04485b/191:100/w_1280,c_limit/Satoshi-Nakamoto-biz-1341874258.jpg",
        publishedAt = "2024-10-09T01:00:00Z",
        content = "Peter Todd is standing on the upper floor of a dilapidated industrial building somewhere in Czechia, chuckling under his breath. He has just been accused on camera of being Satoshi Nakamoto, the Bitc… [+3043 chars]"
    )

    NewsCard(news = sampleNews)
}

