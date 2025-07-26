package com.dev.jetnews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dev.jetnews.R

@Composable
fun DashboardTile(
    label: String,
    imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(140.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                modifier = Modifier
                    .size(84.dp)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Fit
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

data class DashboardTileData(
    val label: String,
    val imageRes: Int,
    val onClick: () -> Unit = {}
)

fun getDashboardTiles(): List<DashboardTileData> {
    return listOf(
        DashboardTileData(
            label = "Trending",
            imageRes = R.drawable.trending,
        ),
        DashboardTileData(
            label = "Politics",
            imageRes = R.drawable.politics,
        ),
        DashboardTileData(
            label = "Sports",
            imageRes = R.drawable.sports,
        ),
        DashboardTileData(
            label = "Environment",
            imageRes = R.drawable.environment,
        ),
        DashboardTileData(
            label = "Stock Market",
            imageRes = R.drawable.stock,
        ),
        DashboardTileData(
            label = "Technology",
            imageRes = R.drawable.technology,
        )
    )
}

