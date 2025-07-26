package com.dev.jetnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dev.jetnews.navigation.AppNavHost
import com.dev.jetnews.ui.DashboardTile
import com.dev.jetnews.ui.getDashboardTiles
import com.dev.jetnews.ui.theme.JetNewsTheme
import com.dev.jetnews.viewmodel.NewsViewModel

class DashboardActivity : ComponentActivity() {

    val viewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController = navController, newsViewModel = viewModel)

        }
    }
}

@Composable
fun DashboardScreen(navController: NavHostController, viewModel: NewsViewModel) {
JetNewsTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Jet News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                val tiles = getDashboardTiles()
                items(count = tiles.size) { index ->
                    val tile = tiles[index]
                    DashboardTile(
                        label = tile.label,
                        imageRes = tile.imageRes,
                        onClick = {
                            viewModel.currentQuery = tile.label
                            navController.navigate("news")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box {
                Card(
                    modifier = Modifier
                        .height(140.dp)
                        .width(140.dp)
                        .clickable { navController.navigate("contact_us") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.contact),
                            contentDescription = "Contact Us",
                            modifier = Modifier
                                .size(72.dp)
                                .align(Alignment.TopCenter),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "Contact Us",
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
        }
    }
}

}

