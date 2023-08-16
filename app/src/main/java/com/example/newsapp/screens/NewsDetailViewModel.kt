package com.example.newsapp.screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.NewsRepositoryImpl
import com.example.newsapp.data.RoomDBService
import com.example.newsapp.data.models.News
import com.example.newsapp.events.NewsDetailEvent
import com.example.newsapp.events.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val repository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var news by mutableStateOf<News?>(null)
        private set

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        Log.d("SAVED STATE HANDLE", savedStateHandle.keys().toString())
        val newsId = savedStateHandle.get<String>("newsId")!!
        viewModelScope.launch {
            news = repository.getNewsById(newsId)
        }
    }

    fun onEvent(event: NewsDetailEvent) {
        when (event) {
            NewsDetailEvent.OnBackButtonClick -> viewModelScope.launch {
                _uiEvent.send(UiEvent.NavigateUp)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository = NewsRepositoryImpl(
                    RoomDBService(this[APPLICATION_KEY] as Application).getInstance().newsDao()
                )
                NewsDetailViewModel(
                    repository = myRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }


}