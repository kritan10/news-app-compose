package com.example.newsapp.events

sealed class NewsDetailEvent {
    object OnBackButtonClick : NewsDetailEvent()
}