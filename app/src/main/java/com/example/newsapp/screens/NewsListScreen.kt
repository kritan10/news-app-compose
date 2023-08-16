package com.example.newsapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.data.models.News
import com.example.newsapp.events.NewsListEvent
import com.example.newsapp.events.UiEvent
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val newsViewModel: NewsListViewModel = viewModel(
        factory = NewsListViewModel.Factory
    )
    val news = newsViewModel.newsList.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1000)
        if (news.value.isEmpty()) {
            newsViewModel.onEvent(NewsListEvent.OnFetchNewsRequest)
        }
    }

    LaunchedEffect(true) {
        newsViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }

    Scaffold {
        if (newsViewModel.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { newsViewModel.onEvent(NewsListEvent.OnFetchNewsRequest) }) {
                            Icon(Icons.Default.Refresh, "Refresh")
                            Text("Fetch news")
                        }
                        Button(onClick = { newsViewModel.onEvent(NewsListEvent.OnDeleteAllNewsClick) }) {
                            Icon(Icons.Default.Delete, "Delete All")
                            Text("Delete all")
                        }
                    }
                }
                items(news.value) { news ->
                    NewsItem(news = news, onEvent = newsViewModel::onEvent)
                    Divider(Modifier.height(2.dp))
                }
            }
        }
    }
}


@Composable
fun NewsItem(
    news: News,
    onEvent: (NewsListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .clickable {
                onEvent(NewsListEvent.OnNewsClick(news))
            }
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(news.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(news.author, fontSize = 18.sp, modifier = Modifier.alpha(0.8F))
        Text(news.published.toString(), fontSize = 16.sp, modifier = Modifier.alpha(0.8F))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = {
                onEvent(NewsListEvent.OnFavouriteClick(news, !news.isFavourite))
            }) {
                Icon(
                    if (news.isFavourite) Icons.Default.Favorite else Icons.Filled.FavoriteBorder,
                    "Favourite"
                )
            }
            IconButton(onClick = { onEvent(NewsListEvent.OnDeleteNewsClick(news)) }) {
                Icon(Icons.Outlined.Delete, "Delete news item")
            }
        }
    }
}
