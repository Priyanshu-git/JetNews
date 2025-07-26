package com.dev.jetnews.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.dev.jetnews.BuildConfig
import com.dev.jetnews.ui.theme.JetNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ContactUsScreen() {
    val context = LocalContext.current
    val emailID = "labsnexx@gmail.com"

    JetNewsTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Contact Us") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "DailyNews",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "DailyNews is a simple and private news app that brings you the latest headlines from public news APIs — without collecting any personal information.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Divider()

                Text(
                    text = "Developer Contact",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Developed by Priyanshu Gaurav",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "For any inquiries or feedback, please reach out to me at",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = emailID,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:$emailID".toUri()
                        }
                        ContextCompat.startActivity(context, intent, null)
                    }
                )

                Divider()

                Text(
                    text = "Important Links",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Privacy Policy",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        val url = "https://github.com/Priyanshu-git/JetNews/blob/master/privacy_policy.md"
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )

                Text(
                    text = "News Data Source",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        val url = "https://newsapi.org" // Replace with actual if different
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )

                Divider()

                Text(
                    text = "App Info",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Version: ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}
