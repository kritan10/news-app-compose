package com.example.newsapp.screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.api.MyRetrofit
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.NewsRepositoryImpl
import com.example.newsapp.data.RoomDBService
import com.example.newsapp.data.utils.NewsMapper
import com.example.newsapp.events.NewsListEvent
import com.example.newsapp.events.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val newsList = repository.getAllNews()
    var isLoading: Boolean by mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NewsListEvent) {
        when (event) {
            is NewsListEvent.OnDeleteNewsClick -> {
                viewModelScope.launch {
                    repository.delete(event.news)
                    _uiEvent.send(UiEvent.ShowSnackbar("News deleted"))
                }
            }

            is NewsListEvent.OnFavouriteClick -> {
                viewModelScope.launch {
                    repository.toggleFavorite(
                        event.news.copy(
                            isFavourite = event.isFavourite
                        )
                    )
                }
            }

            is NewsListEvent.OnNewsClick -> {
                viewModelScope.launch {
                    Log.d("News Item", event.news.id)
                    _uiEvent.send(UiEvent.Navigate("news?newsId=${event.news.id}"))
                }
            }

            is NewsListEvent.OnFetchNewsRequest -> {
                viewModelScope.launch {
                    isLoading = true
                    val response = MyRetrofit().getLatestNews()
                    val news = NewsMapper().newsResToNews(response.news)
                    repository.insertAll(news)
                    isLoading = false
                }
            }

            is NewsListEvent.OnDeleteAllNewsClick -> {
                viewModelScope.launch {
                    repository.nukeTable()
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = NewsRepositoryImpl(
                    RoomDBService(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application).getInstance()
                        .newsDao()
                )
                NewsListViewModel(
                    repository = myRepository,
                )
            }
        }
    }
}