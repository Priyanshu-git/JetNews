package com.dev.jetnews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.jetnews.DashboardScreen
import com.dev.jetnews.NewsScreenComponent
import com.dev.jetnews.ui.ContactUsScreen
import com.dev.jetnews.viewmodel.NewsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    newsViewModel: NewsViewModel,
    startDestination: String = "dashboard"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("dashboard") {
            DashboardScreen(navController, newsViewModel)
        }

        composable("news") {
            NewsScreenComponent(newsViewModel)
        }

        composable("contact_us") {
            ContactUsScreen()
        }
    }
}
