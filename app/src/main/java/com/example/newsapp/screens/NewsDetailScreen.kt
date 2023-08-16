@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.newsapp.data.models.Note
import com.example.newsapp.data.RoomDBService
import com.example.newsapp.data.models.News
import com.example.newsapp.events.NewsDetailEvent
import com.example.newsapp.events.UiEvent
import com.example.newsapp.ui.theme.CardColor
import com.example.newsapp.ui.theme.Purple40
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    onNavigateUp: () -> Unit,
) {
    val newsViewModel: NewsDetailViewModel = viewModel(
        factory = NewsDetailViewModel.Factory
    )
    val news = newsViewModel.news ?: News(
        "", "", "", "", "", "", "", listOf(""), Date(), false
    )
    val note = Note("id", "note", 1L)

    LaunchedEffect(true) {
        newsViewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = { Text("News Detail") }, navigationIcon = {
                    IconButton(onClick = { newsViewModel.onEvent(NewsDetailEvent.OnBackButtonClick) }) {
                        Icon(Icons.Outlined.ArrowBack, "", tint = Color.White)
                    }
                })
        },
        floatingActionButton = {
            if (news.isFavourite) {
                FloatingActionButton(onClick = {}) {
                    Row(Modifier.padding(12.dp)) {
                        Icon(Icons.Outlined.Edit, "Create note")
                        val text = if (note.noteId == 0L) "Create Note" else "Edit Note"
                        Text(text, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }, modifier = Modifier.padding(bottom = 60.dp)
    ) {
        Column(
            Modifier
                .padding(top = it.calculateTopPadding(), start = 4.dp, end = 4.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                news.image, "Image", modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = news.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(news.author, Modifier.alpha(0.8F))
            Spacer(modifier = Modifier.height(10.dp))
            Text(news.published.toString(), Modifier.alpha(0.6F))
            Spacer(modifier = Modifier.height(10.dp))
            Text(news.description)

            if (news.isFavourite && note.noteId != 0L) {
                Spacer(Modifier.height(16.dp))
                Card(
                    backgroundColor = CardColor,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Note", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(note.note, fontSize = 18.sp)
                        Text("NoteID: ${note.noteId}", fontSize = 12.sp)
                        Text("NewsID: ${note.newsId}", fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}