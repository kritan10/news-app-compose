package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.screens.NewsDetailScreen
import com.example.newsapp.screens.NewsListScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                NavHost(navController, "news_list") {
                    composable("news_list") {
                        NewsListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }

                    composable("news?newsId={newsId}",
                        arguments = listOf(
                            navArgument(name = "newsId") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        NewsDetailScreen(onNavigateUp = { navController.navigateUp() })
                    }
                }
            }
        }
    }
}

